package presentation.view

import com.google.gson.GsonBuilder
import presentation.GitHubElementViewModel
import presentation.ProjectViewModel
import presentation.UserViewModel

data class JsonOutput(
    val repos: List<JsonProject>,
    val users: List<JsonUser>
) {
    constructor(stats: List<GitHubElementViewModel>) : this(
        repos = stats.filterIsInstance<ProjectViewModel>().map { JsonProject(it) },
        users = stats.filterIsInstance<UserViewModel>().map { JsonUser(it) }
    )

    override fun toString(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(this)
    }
}