package repositories

import data.persistence.stats.CachedStats
import domain.GitHubElement
import domain.GitHubElementId
import kotlinx.coroutines.*
import repositories.settings.SettingsRepository

class StatsRepository(
    private val statsPersistenceSource: StatsPersistenceSource,
    private val settingsRepository: SettingsRepository,
    private val statsNetworkSource: StatsNetworkSource
) {
    private lateinit var job: Job

    val stats: List<GitHubElement> =
        if (settingsRepository.isCacheValid(statsPersistenceSource.cachedStats.timestamp)) {
            statsPersistenceSource.cachedStats.stats
        } else {
            try {
                getNewStats()
            } catch (e: Exception) {
                // TODO signal that stats are cached in json
                statsPersistenceSource.cachedStats.stats
            }
        }

    private fun getNewStats(): List<GitHubElement> {
        val trackedStatsIds = statsPersistenceSource.trackedStatsIds
        val newStats = runBlocking {
            return@runBlocking statsNetworkSource.getStatList(trackedStatsIds)
        }
        updateStats(newStats)
        return newStats
    }

    @Synchronized
    private fun updateStats(newStats: List<GitHubElement>) {
        // block previous update because it is no longer required
        if (::job.isInitialized){
            runBlocking {
                job.cancelAndJoin()
            }
        }
        job = GlobalScope.launch {
            statsPersistenceSource.cachedStats = CachedStats(newStats)
        }
    }
}

interface StatsPersistenceSource {
    val trackedStatsIds: List<GitHubElementId>
    var cachedStats: CachedStats

}

interface StatsNetworkSource {
    suspend fun getStat(elementId: GitHubElementId): GitHubElement
    suspend fun getStatList(elementsId: List<GitHubElementId>): List<GitHubElement>
}
