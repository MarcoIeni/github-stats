package usecases.getStats

import repositories.settings.ProjectProperties
import repositories.settings.UserProperties
import domain.Project
import domain.User
import repositories.settings.SettingsRepository
import repositories.StatsRepository

class GetStats(
    private val statsRepository: StatsRepository,
    private val settingsRepository: SettingsRepository,
    private val output: GetStatsOutput
) {

    operator fun invoke() {
        val filteredStats: List<FilteredGitHubElement> = statsRepository.stats.map {
            when (it) {
                is Project -> buildFilteredProject(it)
                is User -> buildFilteredUser(it)
            }
        }
        output.presentStats(filteredStats)
    }

    private fun buildFilteredProject(project: Project): FilteredProject {
        val projectProperties: ProjectProperties = settingsRepository.trackedProjectProperties

        return FilteredProject(
            id = project.id,
            stars = getFilteredProperty(projectProperties.stars, project.stars) as Int?,
            commits = getFilteredProperty(projectProperties.commits, project.commits) as Int?,
            branches = getFilteredProperty(projectProperties.branches, project.branches) as Int?,
            forks = getFilteredProperty(projectProperties.forks, project.forks) as Int?,
            closedIssues = getFilteredProperty(projectProperties.closedIssues, project.closedIssues) as Int?,
            openIssues = getFilteredProperty(projectProperties.openIssues, project.openIssues) as Int?,
            projects = getFilteredProperty(projectProperties.projects, project.projects) as Int?,
            contributors = getFilteredProperty(projectProperties.contributors, project.contributors) as Int?,
            releases = getFilteredProperty(projectProperties.releases, project.releases) as Int?,
            closedPulls = getFilteredProperty(projectProperties.closedPulls, project.closedPulls) as Int?,
            openPulls = getFilteredProperty(projectProperties.openPulls, project.openPulls) as Int?,
            watchers = getFilteredProperty(projectProperties.watchers, project.watchers) as Int?

        )
    }

    private fun getFilteredProperty(isPropertyTracked: Boolean, projectField: Number): Number? {
        return if (isPropertyTracked) projectField else null
    }

    private fun buildFilteredUser(user: User): FilteredUser {
        val userProperties: UserProperties = settingsRepository.trackedUserProperties
        return FilteredUser(
            id = user.id,
            followers = getFilteredProperty(userProperties.followers, user.followers) as Int?,
            repositories = getFilteredProperty(userProperties.repositories, user.repositories) as Int?,
            projects = getFilteredProperty(userProperties.projects, user.projects) as Int?,
            stars = getFilteredProperty(userProperties.stars, user.stars) as Int?,
            following = getFilteredProperty(userProperties.following, user.following) as Int?
        )
    }

}

interface GetStatsOutput {
    fun presentStats(stats: List<FilteredGitHubElement>)
}
