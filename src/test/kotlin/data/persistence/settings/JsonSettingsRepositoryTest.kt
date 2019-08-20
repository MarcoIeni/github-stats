package data.persistence.settings

import data.persistence.PersistenceFilePaths
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repositories.settings.ProjectProperties
import repositories.settings.Settings
import repositories.settings.UserProperties

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class JsonSettingsRepositoryTest {

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