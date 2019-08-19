package repositories.settings

interface SettingsRepository {

    val settings: Settings

    val trackedProjectProperties: ProjectProperties
        get() = settings.projectProperties

    val cacheExpiryTime: Int
        get() = settings.cacheExpiryTime

    val trackedUserProperties: UserProperties
        get() = settings.userProperties

    fun isCacheValid(cacheExpiryTime: Int): Boolean =
        false // TODO("if (now - timeWhenDataWereSaved < [cacheExpiryTime]) true else false")
}