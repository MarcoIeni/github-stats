package repositories

import domain.User
import domain.UserId
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import repositories.settings.SettingsRepository
import java.time.LocalDateTime

internal class StatsRepositoryTest {
    @Test
    fun readStatsWhenCacheValid_getCachedStats() {
        val settingsRepository = mockk<SettingsRepository>()
        val statsNetworkSource = mockk<StatsNetworkSource>()
        val statsPersistenceSource = mockk<StatsPersistenceSource>()

        val cachedStats = listOf(
            User(
                id = UserId("name"),
                followers = 1,
                repositories = 2,
                projects = 3,
                stars = 4,
                following = 5
            )
        )

        every { settingsRepository.isCacheValid(any<LocalDateTime>()) } returns true
        every { statsPersistenceSource.cachedStats.stats } returns cachedStats

        // timestamp could be whatever
        every { statsPersistenceSource.cachedStats.timestamp } returns LocalDateTime.now()

        val statsRepository = StatsRepository(
            settingsRepository = settingsRepository,
            statsNetworkSource = statsNetworkSource,
            statsPersistenceSource = statsPersistenceSource
        )

        assertEquals(cachedStats, statsRepository.stats)

    }
}