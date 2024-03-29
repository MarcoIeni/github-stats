package presentation

import usecases.getStats.FilteredGitHubElement
import usecases.getStats.FilteredProject
import usecases.getStats.FilteredUser
import usecases.getStats.GetStatsOutput
import kotlin.properties.Delegates

fun FilteredGitHubElement.toViewModel(): GitHubElementViewModel = when (this) {
    is FilteredProject -> ProjectViewModel(this)
    is FilteredUser -> UserViewModel(this)
}

class StatsPresenter(statsChangeListener: StatsChangeListener) : GetStatsOutput {

    var stats: List<GitHubElementViewModel> by Delegates.observable(
        initialValue = emptyList(),
        onChange = { _, _, new ->
            statsChangeListener.onStatsChange(new)
        })

    override fun presentStats(stats: List<FilteredGitHubElement>) {
        this.stats = stats.map { it.toViewModel() }
    }

}

interface StatsChangeListener {
    fun onStatsChange(newStats: List<GitHubElementViewModel>)
}
