package data.network

import domain.GitHubElement
import domain.User
import domain.UserId
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class UserNetworkScraper (
    private val userId: UserId
) {
    fun scrape(): User {
        val userPage = Jsoup.connect("https://github.com/${userId.name}").get()
        return scrapeUserFromUserPage(userPage)
    }

    internal fun scrapeUserFromUserPage(userPage: Document): User =
        userPage.run {
            val stats = select("nav[aria-label=\"User profile\"] span")
            val statsValues = getIntValuesFromJsoupElementList(stats)

            User(
                id = userId,
                stars = statsValues[2],
                projects = statsValues[1],
                followers = statsValues[3],
                following = statsValues[4],
                repositories = statsValues[0]
            )
        }

}
