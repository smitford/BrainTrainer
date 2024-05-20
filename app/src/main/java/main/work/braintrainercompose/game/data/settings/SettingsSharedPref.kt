package main.work.braintrainercompose.game.data.settings

import android.content.SharedPreferences
import com.google.gson.Gson
import main.work.braintrainercompose.game.domain.api.SettingsRepo
import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.utils.DataUtils.Companion.GAME_SETTINGS

class SettingsSharedPref(private val sharedPref: SharedPreferences, private val gson: Gson) :
    SettingsRepo {
    override fun getSettings(): GameSettings {
        val settingsString = sharedPref.getString(GAME_SETTINGS, null)
        return try {
            gson.fromJson(
                settingsString, GameSettings::class.java
            )
        } catch (e: Exception) {
            GameSettings()
        }
    }

    override fun saveSettings(settings: GameSettings) {
        sharedPref
            .edit()
            .putString(GAME_SETTINGS, gson.toJson(settings))
            .apply()
    }

}