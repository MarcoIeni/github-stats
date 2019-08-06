package data.network

import domain.*
import repositories.StatsNetworkSource


class ScraperStatsNetworkSource : StatsNetworkSource {

    override suspend fun getStatList(elementsId: List<GitHubElementId>): List<GitHubElement> = elementsId.map {
        getStat(it)
    }

    override suspend fun getStat(elementId: GitHubElementId): GitHubElement = when (elementId) {
        is ProjectId -> ProjectNetworkScraper(elementId).scrape()
        is UserId -> UserNetworkScraper(elementId).scrape()
    }
}
