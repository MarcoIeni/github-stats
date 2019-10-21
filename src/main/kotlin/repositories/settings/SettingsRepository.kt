package repositories.settings

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

interface SettingsRepository {

    val settings: Settings

    val trackedProjectProperties: ProjectProperties
        get() = settings.projectProperties

    val cacheExpiryTime: Int
        get() = settings.cacheExpiryTime

    val trackedUserProperties: UserProperties
        get() = settings.userProperties

    fun isCacheValid(cacheTimestamp: LocalDateTime): Boolean {
        val now = LocalDateTime.now()
        val timeDifference: Long = ChronoUnit.SECONDS.between(now, cacheTimestamp)
        return timeDifference < cacheExpiryTime
    }
}