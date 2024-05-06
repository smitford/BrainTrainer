package main.work.braintrainercompose.settings.domain.api

interface ThemeRepo {
    fun saveThemeSettings(isDarkTheme: Boolean)
    fun getThemeSettings(): Boolean?
}