package main.work.braintrainercompose.settings.data

import android.content.SharedPreferences
import main.work.braintrainercompose.settings.domain.api.ThemeRepo
import main.work.braintrainercompose.utils.DataUtils.Companion.THEME_SETTINGS

class ThemeRepoImp(private val sharedPref: SharedPreferences) : ThemeRepo {
    override fun saveThemeSettings(isDarkTheme: Boolean) {
        sharedPref.edit()
            .putBoolean(THEME_SETTINGS, isDarkTheme)
            .apply()
    }

    override fun getThemeSettings(): Boolean? = if (sharedPref.contains(THEME_SETTINGS))
        sharedPref.getBoolean(THEME_SETTINGS, false) else null

}