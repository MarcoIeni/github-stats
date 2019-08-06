package data.persistence.settings

/**
 * User properties that you want to track
 */
data class UserProperties(
    val followers: Boolean,
    val repositories: Boolean,
    val projects: Boolean,
    val stars: Boolean,
    val following: Boolean
)