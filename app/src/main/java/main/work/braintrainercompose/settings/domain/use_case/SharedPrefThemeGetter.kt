package main.work.braintrainercompose.settings.domain.use_case

import main.work.braintrainercompose.settings.domain.ThemeGetterUseCase
import main.work.braintrainercompose.settings.domain.api.ThemeRepo

class SharedPrefThemeGetter(private val repository: ThemeRepo) : ThemeGetterUseCase {
    override fun execute(): Boolean? = repository.getThemeSettings()
}