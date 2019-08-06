package data.persistence.stats

import data.persistence.PersistenceFilePaths
import domain.GitHubElementId
import domain.User
import domain.UserId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class JsonStatsPersistenceSourceTest {

    private fun createJsonStatsPersistenceRepository(): JsonStatsPersistenceSource =
        JsonStatsPersistenceSource(
            PersistenceFilePaths(
                settings = "",
                cache = "", // TODO add this
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
        val expectedTrackedStats =  expectedTrackedStatsObject.usersId + expectedTrackedStatsObject.projectsId
        val jsonRepository = createJsonStatsPersistenceRepository()
        val actualTrackedStats = jsonRepository.trackedStatsIds
        assertEquals(actualTrackedStats, expectedTrackedStats)
    }

    @Test
    fun readCachedStatsFile_ConstructProperObject() {
        val expectedCachedStatsObject = CachedStats(
            stats = listOf(
                User(
                    id = UserId("MarcoIeni"),
                    followers = 888,
                repositories = 161,
            projects = 0,
        stars = 805,
        following = 60
        )
    ,
                User(
                    id = UserId("syl20bnr"),
                    followers= 888,
                    repositories= 161,
                    projects= 0,
                    stars= 805,
                    following= 60
                )
//                {
//                    "id": {
//                    "name": "syl20bnr",
//                    "owner": "spacemacs"
//                },
//                    "stars": 18053,
//                    "commits": 7431,
//                    "branches": 12,
//                    "forks": 4451,
//                    "closedIssues": 4593,
//                    "openIssues": 2245,
//                    "projects": 5,
//                    "contributors": 557,
//                    "releases": 163,
//                    "closedPulls": 5491,
//                    "openPulls": 174,
//                    "watchers": 611
//                },
//                {
//                    "id": {
//                    "name": "MarcoIeni",
//                    "owner": "intelli-space"
//                },
//                    "stars": 18053,
//                    "commits": 7431,
//                    "branches": 12,
//                    "forks": 4451,
//                    "closedIssues": 4593,
//                    "openIssues": 2245,
//                    "projects": 5,
//                    "contributors": 557,
//                    "releases": 163,
//                    "closedPulls": 5491,
//                    "openPulls": 174,
//                    "watchers": 611
//                }
            )
        )
    }
}