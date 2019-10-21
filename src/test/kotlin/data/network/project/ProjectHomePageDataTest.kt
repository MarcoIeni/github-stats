package data.network.project

import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProjectHomePageDataTest {

    @Test
    fun scrapeSpacemacsHomePage_GetProperHomePageData() {
        val expectedHomePageData = ProjectHomePageData(
            openIssues = 2269,
            openPulls = 176,
            projects = 5,
            commits = 7431,
            branches = 12,
            watchers = 612,
            forks = 4489,
            stars = 18204
        )
        val input = File("src/test/resources/html/project/spacemacs/home.html")
        val homePage = Jsoup.parse(input, "UTF-8")
        val actualHomePageData = newProjectHomePageData(homePage)
        assertEquals(actualHomePageData, expectedHomePageData)
    }
}