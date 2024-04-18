package main.work.braintrainercompose.settings.domain.use_case

import main.work.braintrainercompose.settings.domain.ThemeSaverUseCase
import main.work.braintrainercompose.settings.domain.api.ThemeRepo

class SharedPrefThemeSaver(private val repository: ThemeRepo) : ThemeSaverUseCase {
    override fun execute(isDarkTheme: Boolean) {
        repository.saveThemeSettings(isDarkTheme = isDarkTheme)
    }
}