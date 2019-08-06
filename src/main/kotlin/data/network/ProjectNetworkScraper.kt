package data.network

import data.network.project.*
import domain.Project
import domain.ProjectId
import org.jsoup.Jsoup

class ProjectNetworkScraper(
    private val projectId: ProjectId
) {
    fun scrape(): Project {
        val projectUrl = "https://github.com/${projectId.owner}/${projectId.name}"

        val homePage = Jsoup.connect(projectUrl).get()
        val homePageData = newProjectHomePageData(homePage)

        val issuesPage = Jsoup.connect("$projectUrl/issues").get()
        val issuesPageData = newProjectIssuesPageData(issuesPage)

        val pullsPage = Jsoup.connect("$projectUrl/pulls").get()
        val pullsPageData = newProjectPullsPageData(pullsPage)

        return getProject(ProjectNetworkData(homePageData,issuesPageData,pullsPageData))
    }

    fun getProject(
        projectNetworkData: ProjectNetworkData
    ) = Project(
        id = projectId,
        openIssues = projectNetworkData.homePageData.openIssues,
        openPulls = projectNetworkData.homePageData.openPulls,
        projects = projectNetworkData.homePageData.projects,
        commits = projectNetworkData.homePageData.commits,
        branches = projectNetworkData.homePageData.branches,
        releases = projectNetworkData.homePageData.releases,
        contributors = projectNetworkData.homePageData.contributors,
        watchers = projectNetworkData.homePageData.watchers,
        forks = projectNetworkData.homePageData.forks,
        closedIssues = projectNetworkData.issuesPageData.closedIssues,
        closedPulls = projectNetworkData.pullsPageData.closedPulls,
        stars = projectNetworkData.homePageData.stars
    )
}

data class ProjectNetworkData(
    val homePageData: ProjectHomePageData,
    val issuesPageData: ProjectIssuesPageData,
    val pullsPageData: ProjectPullsPageData
)
