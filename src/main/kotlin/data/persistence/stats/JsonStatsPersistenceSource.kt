package data.persistence.stats

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import data.persistence.PersistenceFilePaths
import domain.GitHubElement
import domain.GitHubElementId
import repositories.StatsPersistenceSource
import java.io.File
import com.google.gson.JsonParser
import domain.Project
import domain.User


class JsonStatsPersistenceSource(private val persistenceFilePaths: PersistenceFilePaths): StatsPersistenceSource {
    override var cachedStats: CachedStats
        get() {
            val cachedStatsFile = File(persistenceFilePaths.cache)
            val cachedStatsJson: String = cachedStatsFile.readText()

            val jsonObject = JsonParser().parse(cachedStatsJson).asJsonObject
            val jsonStats = jsonObject.get("stats").asJsonArray;

            val stats = jsonStats.map {
                val isStatProject = it.asJsonObject.has("forks")
                if (isStatProject) {
                    Gson().fromJson(it, Project::class.java)
                }
                else {
                    Gson().fromJson(it, User::class.java)
                }
            }
            return CachedStats(stats)
        }
        set(value) {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val cachedStatsJson: String = gson.toJson(value)
            File(persistenceFilePaths.cache).writeText(cachedStatsJson)
        }

    override val trackedStatsIds: List<GitHubElementId>
        get() {
            val trackedStatsFile = File(persistenceFilePaths.tracked)
            val trackedStatsJson: String = trackedStatsFile.readText()
            val trackedStats = Gson().fromJson(trackedStatsJson, TrackedStats::class.java)
            return trackedStats.usersId + trackedStats.projectsId
        }

}