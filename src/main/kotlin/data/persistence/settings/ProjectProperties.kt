package data.persistence.settings

/**
 * Project properties that you want to track
 */
data class ProjectProperties(
    val stars: Boolean,
    val commits: Boolean,
    val branches: Boolean,
    val forks: Boolean,
    val closedIssues: Boolean,
    val openIssues: Boolean,
    val projects: Boolean,
    val contributors: Boolean,
    val releases: Boolean,
    val closedPulls: Boolean,
    val openPulls: Boolean,
    val watchers: Boolean
)