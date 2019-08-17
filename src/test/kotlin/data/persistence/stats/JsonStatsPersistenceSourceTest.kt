package data.persistence.stats

import data.persistence.PersistenceFilePaths
import domain.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class JsonStatsPersistenceSourceTest {

    private val cachedStats = listOf(
        User(
            id = UserId("MarcoIeni"),
            followers = 7,
            repositories = 12,
            projects = 0,
            stars = 17,
            following = 7
        ),
        User(
            id = UserId("syl20bnr"),
            followers = 912,
            repositories = 161,
            projects = 0,
            stars = 807,
            following = 59
        ),
        Project(
            ProjectId(
                name = "spacemacs",
                owner = "syl20bnr"
            ),
            stars = 18204,
            commits = 7431,
            branches = 12,
            forks = 612,
            closedIssues = 4612,
            openIssues = 2269,
            projects = 5,
            contributors = 558,
            releases = 163,
            closedPulls = 5535,
            openPulls = 176,
            watchers = 612
        ),
        Project(
            ProjectId(
                name = "intelli-space",
                owner = "MarcoIeni"
            ),
            stars = 78,
            commits = 112,
            branches = 1,
            forks = 5,
            closedIssues = 2,
            openIssues = 0,
            projects = 0,
            contributors = 1,
            releases = 0,
            closedPulls = 0,
            openPulls = 0,
            watchers = 5
        )
    )

    private val jsonRepository = JsonStatsPersistenceSource(
        PersistenceFilePaths(
            settings = "",
            cache = "src/test/resources/cache/cache.json",
            tracked = "src/test/resources/tracked/tracked.json"
        )
    )

    @Test
    fun readTrackedStatsFile_ConstructProperObject() {
        val expectedTrackedStatsObject = TrackedStats(
            users = listOf("MarcoIeni", "syl20bnr"),
            projects = listOf(
                TrackedProject(
                    user = "syl20bnr",
                    project = "spacemacs"
                ),
                TrackedProject(
                    user = "MarcoIeni",
                    project = "intelli-space"
                )
            )
        )
        val expectedTrackedStats = expectedTrackedStatsObject.usersId + expectedTrackedStatsObject.projectsId
        val actualTrackedStats = jsonRepository.trackedStatsIds
        assertEquals(actualTrackedStats, expectedTrackedStats)
    }

    @Test
    fun readCachedStatsFile_ConstructProperObject() {
        val expectedCachedStats = cachedStats
        val actualCachedStats = jsonRepository.cachedStats
        assertEquals(actualCachedStats, expectedCachedStats)
    }
}