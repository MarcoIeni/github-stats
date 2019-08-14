package presentation.view

import com.typesafe.config.ConfigException
import domain.ProjectId
import domain.UserId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import presentation.GitHubElementViewModel
import presentation.ProjectViewModel
import presentation.UserViewModel
import usecases.getStats.FilteredGitHubElement
import usecases.getStats.FilteredProject
import usecases.getStats.FilteredUser

internal class StatsViewKtTest {

    @Test
    fun filteredProject_buildCorrectJsonString() {
        val filteredProject = FilteredProject(
            id = ProjectId(name = "projName", owner = "projectOwner"),
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
        val actualJsonString = projectViewModel.toJson()
        val indentedJsonString =
            """
                {
                    "repository":"projectOwner/projName",
                    "stars":1,
                    "commits":2,
                    "branches":3,
                    "forks":4,
                    "closedIssues":5,
                    "openIssues":6,
                    "projects":7,
                    "contributors":8,
                    "releases":9,
                    "closedPulls":10,
                    "watchers":11
                }
            """
        //remove tabs, spaces and new lines
        val expectedJsonString = indentedJsonString.replace("\\s".toRegex(), "")
        assertEquals(actualJsonString, expectedJsonString)
    }

    @Test
    fun filteredUser_buildCorrectJsonString() {
        val filteredUser = FilteredUser(
            id = UserId("name"),
            followers = 1,
            repositories = 2,
            projects = 3,
            stars = 4,
            following = null
        )
        val userViewModel = UserViewModel(filteredUser)
        val actualJsonString = userViewModel.toJson()
        val indentedJsonString =
            """
                {
                    "name":"name",
                    "followers":1,
                    "repositories":2,
                    "projects":3,
                    "stars":4
                }
            """
        //remove tabs, spaces and new lines
        val expectedJsonString = indentedJsonString.replace("\\s".toRegex(), "")
        assertEquals(actualJsonString, expectedJsonString)
    }
}