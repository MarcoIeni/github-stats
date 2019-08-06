package presentation

import domain.User
import usecases.getStats.FilteredProject
import usecases.getStats.FilteredUser

sealed class GitHubElementViewModel

data class ProjectViewModel(
    val id: String,
    val stars: String?,
    val commits: String?,
    val forks: String?,
    val closedIssues: String?,
    val openIssues: String?,
    val projects: String?,
    val contributors: String?,
    val branches: String?,
    val releases: String?,
    val closedPulls: String?,
    val openPulls: String?,
    val watchers: String?
) : GitHubElementViewModel() {
    constructor(project: FilteredProject) : this(
        id = "${project.id.owner}/${project.id.name}",
        stars = project.stars?.toString(),
        commits = project.commits?.toString(),
        forks = project.forks?.toString(),
        closedIssues = project.closedIssues?.toString(),
        openIssues = project.openIssues?.toString(),
        projects = project.projects?.toString(),
        contributors = project.contributors?.toString(),
        branches = project.branches?.toString(),
        releases = project.releases?.toString(),
        closedPulls = project.closedPulls?.toString(),
        openPulls = project.openPulls?.toString(),
        watchers = project.watchers?.toString()
    )
}

data class UserViewModel(
    val name: String,
    val followers: String?,
    val repositories: String?,
    val projects: String?,
    val stars: String?,
    val following: String?
) : GitHubElementViewModel() {
    constructor (user: FilteredUser) : this(
        name = user.id.name,
        followers = user.followers?.toString(),
        repositories = user.repositories?.toString(),
        projects = user.projects?.toString(),
        stars = user.stars?.toString(),
        following = user.following?.toString()
    )
}
