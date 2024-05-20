package main.work.braintrainercompose.settings.domain

interface ThemeSaverUseCase {
    fun execute(isDarkTheme: Boolean)
}