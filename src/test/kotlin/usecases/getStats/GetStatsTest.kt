package usecases.getStats

import data.persistence.PersistenceFilePaths
import data.persistence.settings.JsonSettingsRepository
import domain.Project
import domain.ProjectId
import domain.User
import domain.UserId
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import repositories.StatsRepository

internal class GetStatsTest {

    @Test
    fun getStats_returnProperFilteredStats() {
        val statsRepository = mockk<StatsRepository>()
        val settingsRepository = JsonSettingsRepository(
            PersistenceFilePaths(
                settings = "src/test/resources/settings/get_stats_test_settings.json"
            )
        )

        every { statsRepository.stats } returns listOf(
            Project(
                id = ProjectId(name = "projectName", owner = "projectOwner"),
                stars = 1,
                commits = 2,
                branches = 3,
                forks = 4,
                closedIssues = 5,
                openIssues = 6,
                projects = 7,
                closedPulls = 10,
                openPulls = 22,
                watchers = 11
            ),
            User(
                id = UserId("name"),
                followers = 1,
                repositories = 2,
                projects = 3,
                stars = 4,
                following = 5
            )
        )

        val getStats = GetStats(
            statsRepository = statsRepository,
            output = GetStatsOutputValidator(),
            settingsRepository = settingsRepository
        )

        getStats()
    }

    class GetStatsOutputValidator : GetStatsOutput {
        override fun presentStats(stats: List<FilteredGitHubElement>) {
            val expectedStats = listOf(
                FilteredProject(
                    id = ProjectId(name = "projectName", owner = "projectOwner"),
                    stars = 1,
                    commits = 2,
                    branches = 3,
                    forks = 4,
                    closedIssues = 5,
                    openIssues = 6,
                    projects = 7,
                    closedPulls = 10,
                    openPulls = null,
                    watchers = 11
                ),
                FilteredUser(
                    id = UserId("name"),
                    followers = 1,
                    repositories = 2,
                    projects = 3,
                    stars = 4,
                    following = null
                )
            )
            assertEquals(stats, expectedStats)
        }
    }
}

