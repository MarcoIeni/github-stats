package data.persistence.stats

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import data.persistence.PersistenceFilePaths
import domain.GitHubElement
import domain.GitHubElementId
import repositories.StatsPersistenceSource
import java.io.File
import com.google.gson.JsonParser
import domain.Project
import domain.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class JsonStatsPersistenceSource(private val persistenceFilePaths: PersistenceFilePaths) : StatsPersistenceSource {
    override var cachedStats: CachedStats
        get() {
            val cachedStatsFile = File(persistenceFilePaths.cache)
            val cachedStatsJson: String = cachedStatsFile.readText()

            val jsonObject = JsonParser.parseString(cachedStatsJson).asJsonObject
            val stats = getStats(jsonObject)
            val timestamp = getTimestamp(jsonObject)
            return CachedStats(stats, timestamp)
        }
        set(value) {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val cachedStatsJson = CachedStatsJson(value)
            val cachedStatsJsonString: String = gson.toJson(cachedStatsJson)
            File(persistenceFilePaths.cache).writeText(cachedStatsJsonString)
        }

    private fun getStats(cachedStats: JsonObject): List<GitHubElement> {
        val jsonStats = cachedStats.get("stats").asJsonArray

        val stats = jsonStats.map {
            val isStatProject = it.asJsonObject.has("forks")
            if (isStatProject) {
                Gson().fromJson(it, Project::class.java)
            } else {
                Gson().fromJson(it, User::class.java)
            }
        }
        return stats
    }

    private fun getTimestamp(cachedStats: JsonObject): LocalDateTime {
        val timestampString: String = cachedStats.get("timestamp").asString
        return DateSerializer().getDateFromString(timestampString)
    }

    override val trackedStatsIds: List<GitHubElementId>
        get() {
            val trackedStatsFile = File(persistenceFilePaths.tracked)
            val trackedStatsJson: String = trackedStatsFile.readText()
            val trackedStats = Gson().fromJson(trackedStatsJson, TrackedStats::class.java)
            return trackedStats.usersId + trackedStats.projectsId
        }

}

class DateSerializer {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun getDateFromString(str: String) = LocalDateTime.parse(str, formatter)

    fun getStringFromDate(date: LocalDateTime) = date.format(formatter)

}

class CachedStatsJson(cachedStats: CachedStats) {
    val timestamp: String = DateSerializer().getStringFromDate(cachedStats.timestamp)
    val stats: List<GitHubElement> = cachedStats.stats
}