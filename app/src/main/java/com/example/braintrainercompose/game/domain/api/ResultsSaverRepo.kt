package com.example.braintrainercompose.game.domain.api

import com.example.braintrainercompose.game.domain.models.GameResults

interface ResultsSaverRepo {
    fun saveResults(results: GameResults, name: String, difficulty: String)
}