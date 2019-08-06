package data.network.project

import org.jsoup.nodes.Document

fun newProjectIssuesPageData(issuesPage: Document): ProjectIssuesPageData = issuesPage.run {
    val closedIssuesElement = select("#js-issues-toolbar div[class=\"flex-auto\"] a[class=\"btn-link\"]")
    val closedIssuesString = closedIssuesElement.text().split(" ")[0]
    val closedIssues = closedIssuesString.replace(",", "").toInt()
    return ProjectIssuesPageData(closedIssues)
}

data class ProjectIssuesPageData(
    val closedIssues: Int
)
