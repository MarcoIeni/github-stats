package domain

sealed class GitHubElement {
    abstract val id: GitHubElementId
}

data class Project (
    override val id: ProjectId,
    val stars: Int,
    val commits: Int,
    val branches: Int,
    val forks: Int,
    val closedIssues: Int,
    val openIssues: Int,
    val projects: Int,
    val closedPulls: Int,
    val openPulls: Int,
    val watchers: Int
) : GitHubElement()

data class User(
    override val id: UserId,
    val followers: Int,
    val repositories: Int,
    val projects: Int,
    val stars: Int,
    val following: Int
) : GitHubElement()
