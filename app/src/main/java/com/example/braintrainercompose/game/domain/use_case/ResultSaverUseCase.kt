package com.example.braintrainercompose.game.domain.use_case

import com.example.braintrainercompose.game.domain.models.GameResults

interface ResultSaverUseCase {
    suspend fun execute(results: GameResults, name: String, difficulty: String)
}