package data.persistence.stats

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import data.persistence.PersistenceFilePaths
import domain.GitHubElement
import domain.GitHubElementId
import domain.ProjectId
import domain.UserId
import repositories.StatsPersistenceSource
import java.io.File

class JsonStatsPersistenceSource(private val persistenceFilePaths: PersistenceFilePaths): StatsPersistenceSource {
    override var cachedStats: List<GitHubElement>
        get() {
            val cachedStatsFile = File(persistenceFilePaths.cache)
            val cachedStatsJson: String = cachedStatsFile.readText()
            val cached = Gson().fromJson(cachedStatsJson, CachedStats::class.java)
            return cached.stats
        }
        set(value) {
            val cached = CachedStats(value)
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val cachedStatsJson: String = gson.toJson(cached)
            File("data/cache.json").writeText(cachedStatsJson)
        }

    override val trackedStatsIds: List<GitHubElementId>
        get() {
            val trackedStatsFile = File(persistenceFilePaths.tracked)
            val trackedStatsJson: String = trackedStatsFile.readText()
            val trackedStats = Gson().fromJson(trackedStatsJson, TrackedStats::class.java)
            return trackedStats.usersId + trackedStats.projectsId
        }

}