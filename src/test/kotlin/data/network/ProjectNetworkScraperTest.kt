package data.network

import data.network.project.ProjectHomePageData
import data.network.project.ProjectIssuesPageData
import data.network.project.ProjectPullsPageData
import domain.Project
import domain.ProjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ProjectNetworkScraperTest {
    @Test
    fun fromProjectData_buildCorrectProject() {
        val projectId = ProjectId(
            name = "projectName",
            owner = "projectOwner"
        )

        val expectedProject = Project(
            id = projectId,
            stars = 1,
            commits = 2,
            branches = 3,
            forks = 4,
            openIssues = 5,
            projects = 6,
            contributors = 7,
            releases = 8,
            openPulls = 9,
            watchers = 10,
            closedIssues = 11,
            closedPulls = 12
        )

        val projectNetworkData = ProjectNetworkData(
            homePageData = ProjectHomePageData(
                stars = 1,
                commits = 2,
                branches = 3,
                forks = 4,
                openIssues = 5,
                projects = 6,
                contributors = 7,
                releases = 8,
                openPulls = 9,
                watchers = 10
            ),
            issuesPageData = ProjectIssuesPageData(
                closedIssues = 11
            ),
            pullsPageData = ProjectPullsPageData(
                closedPulls = 12
            )
        )

        val actualProject = ProjectNetworkScraper(projectId).getProject(projectNetworkData)
        assertEquals(actualProject, expectedProject)

    }
}