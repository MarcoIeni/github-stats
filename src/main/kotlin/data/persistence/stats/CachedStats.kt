package data.persistence.stats

import domain.GitHubElement
import java.time.LocalDateTime

data class CachedStats(
    val stats: List<GitHubElement>,
    val timestamp: LocalDateTime
)

