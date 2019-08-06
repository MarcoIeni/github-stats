package data.persistence.stats

import domain.ProjectId
import domain.UserId

data class TrackedStats(
    private val users: List<String>,
    private val projects: List<TrackedProject>
) {
    val usersId: List<UserId>
        get() = users.map { UserId(it) }

    val projectsId: List<ProjectId>
        get() = projects.map { ProjectId(name = it.project, owner = it.user) }
}

data class TrackedProject(
    val user: String,
    val project: String
)