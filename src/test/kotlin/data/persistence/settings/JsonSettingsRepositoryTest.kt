package data.persistence.settings

import data.persistence.PersistenceFilePaths
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestTemplate
import repositories.settings.ProjectProperties
import repositories.settings.Settings
import repositories.settings.SettingsRepository
import repositories.settings.UserProperties
import java.io.File
import java.time.LocalDateTime

const val CACHE_EXP_TIME_10_FILEPATH = "src/test/resources/settings/cache_exp_time_10.json"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class JsonSettingsRepositoryTest {

    private fun getTimeStampString(time: LocalDateTime) = """
              "timestamp": {
                "date": {
                  "year": ${time.year},
                  "month": ${time.monthValue},
                  "day": ${time.dayOfMonth}
                },
                "time": {
                  "hour": ${time.hour},
                  "minute": ${time.minute},
                  "second": ${time.second},
                  "nano": ${time.nano}
                }
              }
        """.trimIndent()

    @Test
    fun checkExpiredCache_isNotValid() {
        val expiredCacheFilepath = "src/test/resources/cache/expiredCache.json"
        val now = LocalDateTime.now()
        val timestampString = getTimeStampString(now)
        val expiredCacheFileContent = """
            {
                $timestampString
                "stats": []
            }
        """.trimIndent()
        val expiredCacheFile = File(expiredCacheFilepath)
        expiredCacheFile.writeText(expiredCacheFileContent)
        val settingsRepository = JsonSettingsRepository(
            PersistenceFilePaths(
                cache = expiredCacheFilepath,
                settings = CACHE_EXP_TIME_10_FILEPATH
            )
        )
        val elevenSecondsFromNow = now.plusSeconds(11)
        val isCacheValid = settingsRepository.isCacheValid(elevenSecondsFromNow)

        // cache expiry time is 10 seconds and 11 seconds have passed
        // since cache was saved, so the cache should be not valid
        assert(!isCacheValid)

        expiredCacheFile.delete()
    }

    @Test
    fun checkRecentlyCreatedCache_isValid() {
        val recentCacheFilepath = "src/test/resources/cache/recentCache.json"
        val now = LocalDateTime.now()
        val timestampString = getTimeStampString(now)
        val recentCacheFileContent = """
            {
                $timestampString
                "stats": []
            }
        """.trimIndent()
        val recentCacheFile = File(recentCacheFilepath)
        recentCacheFile.writeText(recentCacheFileContent)
        val settingsRepository = JsonSettingsRepository(
            PersistenceFilePaths(
                cache = recentCacheFilepath,
                settings = CACHE_EXP_TIME_10_FILEPATH
            )
        )
        val fiveSecondsFromNow = now.plusSeconds(5)
        val isCacheValid = settingsRepository.isCacheValid(fiveSecondsFromNow)

        // cache expiry time is 10 seconds and only 5 seconds have passed
        // since cache was saved, so the cache should be still valid
        assert(isCacheValid)

        recentCacheFile.delete()
    }

    @Test
    fun readSettingsFile_ConstructProperObject() {
        val expectedSettings = Settings(
            cacheExpiryTime = 5,
            projectProperties = ProjectProperties(
                stars = true,
                commits = true,
                branches = true,
                forks = true,
                closedIssues = true,
                openIssues = true,
                projects = false,
                contributors = true,
                releases = false,
                closedPulls = true,
                openPulls = true,
                watchers = true
            ),
            userProperties = UserProperties(
                followers = true,
                repositories = true,
                projects = false,
                stars = true,
                following = true
            )
        )

        val jsonRepository = JsonSettingsRepository(
            PersistenceFilePaths(
                settings = "src/test/resources/settings/valid_settings.json"
            )
        )

        val actualSettings = jsonRepository.settings
        assertEquals(actualSettings, expectedSettings)
    }
}