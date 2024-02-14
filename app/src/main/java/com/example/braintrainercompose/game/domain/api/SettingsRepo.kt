package com.example.braintrainercompose.game.domain.api

import com.example.braintrainercompose.game.domain.models.GameSettings

interface SettingsRepo {
    fun getSettings(): GameSettings
    fun saveSettings(settings: GameSettings)
}