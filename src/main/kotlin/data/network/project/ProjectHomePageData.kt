package data.network.project

import data.network.getIntValuesFromJsoupElementList
import org.jsoup.nodes.Document

fun newProjectHomePageData(homePage: Document): ProjectHomePageData = homePage.run {
    val navElements = select("#js-repo-pjax-container div nav span[class=\"counter\"]")
    val navValues = getIntValuesFromJsoupElementList(navElements)
    val secondBar = select("ul[class=\"numbers-summary\"] span[class=\"num text-emphasized\"]")
    val someSocialCount = select("ul[class=\"pagehead-actions\"] a")// a[class=\"social-count js-social-count\"]")
    val forksElem = select("ul[class=\"pagehead-actions\"] a[href\$=\"members\"]")
    val someSocialCountValues = getIntValuesFromJsoupElementList(someSocialCount)
    val secondBarValues = getIntValuesFromJsoupElementList(secondBar)

    val commits = secondBarValues[0]
    val branches = secondBarValues[1]

    return ProjectHomePageData(
        openIssues = navValues[0],
        openPulls = navValues[1],
        projects = navValues[2],
        commits = commits,
        branches = branches,
        watchers = someSocialCountValues[0],
        forks = getIntValuesFromJsoupElementList(forksElem)[0],
        stars = someSocialCountValues[1]
    )
}

data class ProjectHomePageData(
    val stars: Int,
    val commits: Int,
    val branches: Int,
    val forks: Int,
    val openIssues: Int,
    val projects: Int,
    val openPulls: Int,
    val watchers: Int
)

