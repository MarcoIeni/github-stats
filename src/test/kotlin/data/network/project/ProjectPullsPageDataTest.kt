package data.network.project

import data.network.project.ProjectPullsPageData
import data.network.project.newProjectPullsPageData
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class ProjectPullsPageDataTest{

    @Test
    fun scrapeSpacemacsPullsPage_GetProperPullsPageData() {
        val expectedPullsPageData = ProjectPullsPageData(
            closedPulls = 5535
        )
        val input = File("src/test/resources/html/project/spacemacs/pulls.html")
        val pullsPage = Jsoup.parse(input, "UTF-8")
        val actualPullsPageData = newProjectPullsPageData(pullsPage)
        assertEquals(actualPullsPageData, expectedPullsPageData)
    }
}