package main.work.braintrainercompose.game.domain.use_case

import main.work.braintrainercompose.game.domain.models.GameSettings

interface SettingsInteractor {
    fun getSettings(): GameSettings
    fun saveSettings(settings: GameSettings)
}