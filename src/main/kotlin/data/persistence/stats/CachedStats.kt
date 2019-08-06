package data.persistence.stats

import domain.GitHubElement

data class CachedStats(
    val stats: List<GitHubElement>
)