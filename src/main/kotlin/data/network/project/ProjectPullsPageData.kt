package data.network.project

import org.jsoup.nodes.Document

fun newProjectPullsPageData(pullsPage: Document): ProjectPullsPageData = pullsPage.run {
    val closedPullsElement = select("#js-issues-toolbar div[class=\"flex-auto\"] a[class=\"btn-link\"]")
    val closedPullsString = closedPullsElement.text().split(" ")[0]
    val closedPulls = closedPullsString.replace(",", "").toInt()
    return ProjectPullsPageData(closedPulls)
}

data class ProjectPullsPageData(
    val closedPulls: Int
)
