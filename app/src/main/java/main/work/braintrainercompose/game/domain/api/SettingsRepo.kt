package main.work.braintrainercompose.game.domain.api

import main.work.braintrainercompose.game.domain.models.GameSettings

interface SettingsRepo {
    fun getSettings(): GameSettings
    fun saveSettings(settings: GameSettings)
}