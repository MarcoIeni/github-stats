package usecases.getStats

import domain.GitHubElementId
import domain.ProjectId
import domain.UserId

/**
 * This class is called filtered because if the property is not tracked by the
 * user, then it is null.
 */
sealed class FilteredGitHubElement {
    abstract val id: GitHubElementId
}

data class FilteredProject(
    override val id: ProjectId,
    val stars: Int?,
    val commits: Int?,
    val branches: Int?,
    val forks: Int?,
    val closedIssues: Int?,
    val openIssues: Int?,
    val projects: Int?,
    val contributors: Int?,
    val releases: Int?,
    val closedPulls: Int?,
    val openPulls: Int?,
    val watchers: Int?
) : FilteredGitHubElement()

data class FilteredUser(
    override val id: UserId,
    val followers: Int?,
    val repositories: Int?,
    val projects: Int?,
    val stars: Int?,
    val following: Int?
) : FilteredGitHubElement()
