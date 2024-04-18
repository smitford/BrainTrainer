package main.work.braintrainercompose.settings.ui.view_model

import androidx.lifecycle.ViewModel
import main.work.braintrainercompose.settings.domain.ThemeGetterUseCase
import main.work.braintrainercompose.settings.domain.ThemeSaverUseCase
import main.work.braintrainercompose.settings.domain.UrlSenderUseCase

class SettingsViewModel(
    private val themeGetterUseCase: ThemeGetterUseCase,
    private val themeSaverUseCase: ThemeSaverUseCase,
    private val urlSenderUseCase: UrlSenderUseCase
) :
    ViewModel() {
    fun getTheme(): Boolean = themeGetterUseCase.execute()

    fun saveTheme(isDarkTheme: Boolean) {
        themeSaverUseCase.execute(isDarkTheme = isDarkTheme)
    }

    fun shareApp() {
        urlSenderUseCase.execute(url = "")
    }
}