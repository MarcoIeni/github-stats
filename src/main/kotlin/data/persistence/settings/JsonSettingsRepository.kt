package data.persistence.settings

import com.google.gson.Gson
import data.persistence.PersistenceFilePaths
import repositories.SettingsRepository
import java.io.File

class JsonSettingsRepository(private val persistenceFilePaths: PersistenceFilePaths) : SettingsRepository {
    override val settings: Settings
        get() {
            val settingsFile = File(persistenceFilePaths.settings)
            val settingsJson: String = settingsFile.readText()
            return Gson().fromJson(settingsJson, Settings::class.java)
        }
}