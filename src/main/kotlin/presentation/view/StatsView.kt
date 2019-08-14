package presentation.view

import com.github.ajalt.clikt.output.TermUi.echo
import com.google.gson.Gson
import presentation.StatsChangeListener
import presentation.GitHubElementViewModel
import presentation.ProjectViewModel
import presentation.UserViewModel

fun GitHubElementViewModel.toJson(): String {
    val jsonGitHubElement = when (this) {
        is UserViewModel -> JsonUser(this)
        is ProjectViewModel -> JsonProject(this)
    }
    return Gson().toJson(jsonGitHubElement)
}

class StatsView : StatsChangeListener {
    override fun onStatsChange(newStats: List<GitHubElementViewModel>) {
        newStats.forEach {
            echo(it.toJson())
        }
    }
}