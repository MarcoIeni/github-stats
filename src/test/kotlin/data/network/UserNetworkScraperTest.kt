package data.network

import domain.User
import domain.UserId
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class UserNetworkScraperTest {

    @Test
    fun scrapeSyl20bnrUser_GetProperUserData()
    {
        val userId = UserId("syl20bnr")
        val expectedUser = User(
            id = userId,
            followers = 912,
            repositories = 161,
            projects = 0,
            stars = 808,
            following = 59
        )
        val input = File("src/test/resources/html/user/syl20bnr.html")
        val userPage = Jsoup.parse(input, "UTF-8")
        val actualUser = UserNetworkScraper(userId).scrape()
        assertEquals(actualUser, expectedUser)
    }
}