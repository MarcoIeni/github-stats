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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class JsonSettingsRepositoryTest {

    @Test
    fun checkRecentlyCreatedCache_isValid() {
        val recentCacheFilepath = "src/test/resources/cache/recentCache.json"
        val now = LocalDateTime.now()
        val recentCacheFileContent = """
            {
              "timestamp": {
                "date": {
                  "year": ${now.year},
                  "month": ${now.monthValue},
                  "day": ${now.dayOfMonth}
                },
                "time": {
                  "hour": ${now.hour},
                  "minute": ${now.minute},
                  "second": ${now.second},
                  "nano": ${now.nano}
                }
              },
              "stats": []
            }
        """.trimIndent()
        val recentCacheFile = File(recentCacheFilepath)
        recentCacheFile.writeText(recentCacheFileContent)
        val settingsRepository = JsonSettingsRepository(PersistenceFilePaths(
            cache = recentCacheFilepath,
            settings = "src/test/resources/settings/cache_exp_time_10.json"
        ))
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