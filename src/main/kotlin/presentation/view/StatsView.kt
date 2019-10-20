package presentation.view

import presentation.StatsChangeListener
import presentation.GitHubElementViewModel

class StatsView : StatsChangeListener {
    override fun onStatsChange(newStats: List<GitHubElementViewModel>) {
        val jsonOutput = JsonOutput(newStats)
        println(jsonOutput)
    }
}