package usecases.getStats

import data.persistence.settings.ProjectProperties
import data.persistence.settings.UserProperties
import data.persistence.stats.TrackedProject
import domain.Project
import domain.ProjectId
import domain.User
import domain.UserId
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import repositories.SettingsRepository
import repositories.StatsRepository

internal class GetStatsTest {

    @Test
    fun getStats_returnProperFilteredStats() {
        val statsRepository = mockk<StatsRepository>()
        val settingsRepository = mockk<SettingsRepository>()

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
                contributors = 8,
                releases = 9,
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

        every { settingsRepository.trackedProjectProperties } returns ProjectProperties(
            stars = true,
            commits = true,
            branches = true,
            forks = true,
            closedIssues = true,
            openIssues = true,
            projects = true,
            contributors = true,
            releases = true,
            closedPulls = true,
            openPulls = false,
            watchers = true
        )

        every { settingsRepository.trackedUserProperties } returns UserProperties(
            followers = true,
            repositories = true,
            projects = true,
            stars = true,
            following = false
        )

        val getStats = GetStats(
            statsRepository = statsRepository,
            output = GetStatsOutputValidator(),
            settingsRepository = settingsRepository
        )

        getStats()

    }

    class GetStatsOutputValidator : GetStatsOutput {
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
                contributors = 8,
                releases = 9,
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

        override fun presentStats(stats: List<FilteredGitHubElement>) {
            assertEquals(stats, expectedStats)
        }
    }
}

