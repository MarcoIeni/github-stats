package presentation.view

import com.github.ajalt.clikt.output.TermUi.echo
import com.google.gson.Gson
import presentation.StatsChangeListener
import presentation.GitHubElementViewModel
import presentation.ProjectViewModel
import presentation.UserViewModel

class StatsView : StatsChangeListener {
    override fun onStatsChange(newStats: List<GitHubElementViewModel>) {
        val gson = Gson()
        newStats.forEach {
            val jsonGitHubElement: JsonGitHubElement = when (it) {
                is UserViewModel -> JsonUser(it)
                is ProjectViewModel -> JsonProject(it)
            }
            echo(gson.toJson(jsonGitHubElement))
        }
    }
}