package domain

sealed class GitHubElementId

data class ProjectId (
    val name: String,
    val owner: String
) : GitHubElementId()

data class UserId(
    val name: String
) : GitHubElementId()
