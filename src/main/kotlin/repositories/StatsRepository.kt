package repositories

import kotlinx.coroutines.*

import domain.GitHubElement
import domain.GitHubElementId
import repositories.settings.SettingsRepository

class StatsRepository(
    private val statsPersistenceSource: StatsPersistenceSource,
    private val settingsRepository: SettingsRepository,
    private val statsNetworkSource: StatsNetworkSource
) {
    private var areStatsUpdating: Boolean = false

    val stats: List<GitHubElement>
        get() =
            if (settingsRepository.isCacheValid(settingsRepository.cacheExpiryTime)) {
                statsPersistenceSource.cachedStats
            } else {
                getNewStats()
            }

    private fun getNewStats(): List<GitHubElement> {
        val trackedStatsIds = statsPersistenceSource.trackedStatsIds
        println(trackedStatsIds)
        val newStats = runBlocking {
            return@runBlocking statsNetworkSource.getStatList(trackedStatsIds)
        }
        updateStats(newStats)
        return newStats
    }

    @Synchronized
    fun updateStats(newStats: List<GitHubElement>) {
        if (!areStatsUpdating) {
            areStatsUpdating = true
            GlobalScope.launch {
                statsPersistenceSource.cachedStats = newStats
                areStatsUpdating = false
            }
        }
    }
}

interface StatsPersistenceSource {
    val trackedStatsIds: List<GitHubElementId>
    var cachedStats: List<GitHubElement>

}

interface StatsNetworkSource {
    suspend fun getStat(elementId: GitHubElementId): GitHubElement
    suspend fun getStatList(elementsId: List<GitHubElementId>): List<GitHubElement>
}
