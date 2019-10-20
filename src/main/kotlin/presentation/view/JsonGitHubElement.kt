package presentation.view
import presentation.ProjectViewModel
import presentation.UserViewModel

sealed class JsonGitHubElement

data class JsonProject(
    val repository: String,
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
) : JsonGitHubElement() {
    constructor(projectViewModel: ProjectViewModel) : this(
        repository = projectViewModel.id,
        stars = projectViewModel.stars?.toInt(),
        commits = projectViewModel.commits?.toInt(),
        branches = projectViewModel.branches?.toInt(),
        forks = projectViewModel.forks?.toInt(),
        closedIssues = projectViewModel.closedIssues?.toInt(),
        openIssues = projectViewModel.openIssues?.toInt(),
        projects = projectViewModel.projects?.toInt(),
        contributors = projectViewModel.contributors?.toInt(),
        releases = projectViewModel.releases?.toInt(),
        closedPulls = projectViewModel.closedPulls?.toInt(),
        openPulls = projectViewModel.openPulls?.toInt(),
        watchers = projectViewModel.watchers?.toInt()
    )
}

data class JsonUser(
    val name: String,
    val followers: Int?,
    val repositories: Int?,
    val projects: Int?,
    val stars: Int?,
    val following: Int?
) : JsonGitHubElement() {
    constructor(userViewModel: UserViewModel) : this(
        name = userViewModel.name,
        followers = userViewModel.followers?.toInt(),
        repositories = userViewModel.repositories?.toInt(),
        projects = userViewModel.projects?.toInt(),
        stars = userViewModel.stars?.toInt(),
        following = userViewModel.following?.toInt()
    )
}
