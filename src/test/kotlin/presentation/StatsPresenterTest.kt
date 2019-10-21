package presentation

import domain.ProjectId
import domain.UserId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import usecases.getStats.FilteredProject
import usecases.getStats.FilteredUser

internal class StatsPresenterTest {

    private val filteredUser = FilteredUser(
        id = UserId("name"),
        followers = 1,
        repositories = 2,
        projects = 3,
        stars = 4,
        following = null
    )

    private val filteredProject = FilteredProject(
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
    )

    @Test
    fun presentStats_buildProperObject() {
        val statsChangeListenerTest = StatsChangeListenerTest(filteredUser, filteredProject)
        val statsPresenter = StatsPresenter(statsChangeListenerTest)
        val statsToPresent = listOf(filteredProject, filteredUser)
        statsPresenter.presentStats(statsToPresent)
    }

}

class StatsChangeListenerTest(
    private val filteredUser: FilteredUser,
    private val filteredProject: FilteredProject
) : StatsChangeListener {

    override fun onStatsChange(newStats: List<GitHubElementViewModel>) {
        val projectViewModel = ProjectViewModel(filteredProject)

        val userViewModel = UserViewModel(filteredUser)

        val expectedStats = listOf(
            projectViewModel,
            userViewModel
        )
        assertEquals(newStats, expectedStats)
    }
}
