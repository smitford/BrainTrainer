package com.example.braintrainercompose.game.domain.use_case.implement

import com.example.braintrainercompose.game.domain.api.SettingsRepo
import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.use_case.SettingsInteractor

class SettingsInteractorImp(val repository: SettingsRepo) : SettingsInteractor {
    override fun getSettings(): GameSettings =
        repository.getSettings()


    override fun saveSettings(settings: GameSettings) =
        repository.saveSettings(settings = settings)
}