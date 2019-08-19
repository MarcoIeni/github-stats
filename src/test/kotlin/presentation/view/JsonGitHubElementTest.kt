package presentation.view

import domain.ProjectId
import domain.UserId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import presentation.ProjectViewModel
import presentation.UserViewModel
import usecases.getStats.FilteredProject
import usecases.getStats.FilteredUser

internal class JsonGitHubElementTest {

    @Test
    fun createJsonProjectFromViewModel_buildProperObject() {
        val filteredProject = FilteredProject(
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
        )
        val projectViewModel = ProjectViewModel(filteredProject)
        val actualJsonProject = JsonProject(projectViewModel)
        val expectedJsonProject = JsonProject(
            repository = "projectOwner/projectName",
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
        )
        assertEquals(actualJsonProject, expectedJsonProject)
    }

    @Test
    fun createJsonUserFromViewModel_buildProperObject() {
        val filteredUser = FilteredUser(
            id = UserId("username"),
            followers = 1,
            repositories = 2,
            projects = 3,
            stars = 4,
            following = null
        )
        val userViewModel = UserViewModel(filteredUser)
        val actualJsonUser = JsonUser(userViewModel)

        val expectedJsonUser = JsonUser(
            name = "username",
            followers = 1,
            repositories = 2,
            projects = 3,
            stars = 4,
            following = null
        )
        assertEquals(actualJsonUser, expectedJsonUser)
    }
}