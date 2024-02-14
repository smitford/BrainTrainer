package com.example.braintrainercompose.game.domain.use_case

import com.example.braintrainercompose.game.domain.models.GameSettings

interface SettingsInteractor {
    fun getSettings(): GameSettings
    fun saveSettings(settings: GameSettings)
}