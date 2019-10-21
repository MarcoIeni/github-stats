package presentation.view

import domain.ProjectId
import domain.UserId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import presentation.GitHubElementViewModel
import presentation.ProjectViewModel
import presentation.UserViewModel
import usecases.getStats.FilteredProject
import usecases.getStats.FilteredUser

internal class JsonOutputTest {

    val stats: List<GitHubElementViewModel>
    get() {
        val filteredProject = FilteredProject(
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
        val projectViewModel = ProjectViewModel(filteredProject)

        val filteredUser = FilteredUser(
            id = UserId("name"),
            followers = 1,
            repositories = 2,
            projects = 3,
            stars = 4,
            following = null
        )
        val userViewModel = UserViewModel(filteredUser)

        return listOf(projectViewModel,userViewModel)
    }

    @Test
    fun filteredProject_buildCorrectJsonString() {
        val indentedJsonString =
            """
            {
                "repos": [
                    {
                        "repository":"projectOwner/projectName",
                        "stars":1,
                        "commits":2,
                        "branches":3,
                        "forks":4,
                        "closedIssues":5,
                        "openIssues":6,
                        "projects":7,
                        "closedPulls":10,
                        "watchers":11
                    }
                ],
                "users": [
                    {
                        "name":"name",
                        "followers":1,
                        "repositories":2,
                        "projects":3,
                        "stars":4
                    }
                ]
            }
            """

        val output = JsonOutput(stats).toString()

        val expectedJsonString = indentedJsonString.compact()
        val actualJsonString = output.compact()

        assertEquals(actualJsonString, expectedJsonString)
    }

    /**
     * remove tabs, spaces and new lines
     */
    private fun String.compact(): String {
        return this.replace("\\s".toRegex(), "")
    }

}