package main.work.braintrainercompose.settings.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val theme = MutableLiveData<Boolean?>()

    init {
        theme.value = loadTheme()
    }

    fun getTheme(): LiveData<Boolean?> = theme

    fun saveTheme(isDarkTheme: Boolean) {
        themeSaverUseCase.execute(isDarkTheme = isDarkTheme)
        theme.value = loadTheme()
    }

    private fun loadTheme(): Boolean? = themeGetterUseCase.execute()

    fun shareApp() {
        urlSenderUseCase.execute(url = "https://play.google.com/store/apps/details?id=main.work.braintrainercompose&hl=en-US&ah=sImgtVDKPHcPNvFao_Abe6AyElw")
    }
}