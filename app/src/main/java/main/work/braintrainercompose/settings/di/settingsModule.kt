package main.work.braintrainercompose.settings.di

import main.work.braintrainercompose.settings.data.DataSenderRepoImp
import main.work.braintrainercompose.settings.data.ThemeRepoImp
import main.work.braintrainercompose.settings.domain.ThemeGetterUseCase
import main.work.braintrainercompose.settings.domain.ThemeSaverUseCase
import main.work.braintrainercompose.settings.domain.UrlSenderUseCase
import main.work.braintrainercompose.settings.domain.api.DataSenderRepo
import main.work.braintrainercompose.settings.domain.api.ThemeRepo
import main.work.braintrainercompose.settings.domain.use_case.SharedPrefThemeGetter
import main.work.braintrainercompose.settings.domain.use_case.SharedPrefThemeSaver
import main.work.braintrainercompose.settings.domain.use_case.UrlSenderImp
import main.work.braintrainercompose.settings.ui.view_model.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    single<DataSenderRepo> { DataSenderRepoImp(context = get()) }
    single<ThemeRepo> { ThemeRepoImp(sharedPref = get()) }
    factory<ThemeGetterUseCase> { SharedPrefThemeGetter(repository = get()) }
    factory<ThemeSaverUseCase> { SharedPrefThemeSaver(repository = get()) }
    factory<UrlSenderUseCase> { UrlSenderImp(repository = get()) }
    viewModel {
        SettingsViewModel(
            themeGetterUseCase = get(),
            themeSaverUseCase = get(),
            urlSenderUseCase = get()
        )
    }
}