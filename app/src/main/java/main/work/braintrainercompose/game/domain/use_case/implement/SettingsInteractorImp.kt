package main.work.braintrainercompose.game.domain.use_case.implement

import main.work.braintrainercompose.game.domain.api.SettingsRepo
import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.use_case.SettingsInteractor

class SettingsInteractorImp(val repository: SettingsRepo) : SettingsInteractor {
    override fun getSettings(): GameSettings =
        repository.getSettings()


    override fun saveSettings(settings: GameSettings) =
        repository.saveSettings(settings = settings)
}