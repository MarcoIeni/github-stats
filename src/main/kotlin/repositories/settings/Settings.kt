package repositories.settings

data class Settings (
    val cacheExpiryTime: Int,
    val userProperties: UserProperties,
    val projectProperties: ProjectProperties
)