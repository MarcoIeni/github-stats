package data.network.project

import data.network.project.ProjectIssuesPageData
import data.network.project.newProjectIssuesPageData
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class ProjectIssuesPageDataTest {

    @Test
    fun scrapeSpacemacsIssuesPage_GetProperIssuesPageData() {
        val expectedIssuesPageData = ProjectIssuesPageData(
            closedIssues = 4612
        )
        val input = File("src/test/resources/html/project/spacemacs/issues.html")
        val issuesPage = Jsoup.parse(input, "UTF-8")
        val actualIssuesPageData = newProjectIssuesPageData(issuesPage)
        assertEquals(actualIssuesPageData, expectedIssuesPageData)
    }
}