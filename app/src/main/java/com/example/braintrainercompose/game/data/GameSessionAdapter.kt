package com.example.braintrainercompose.game.data

import com.example.braintrainercompose.game.data.models.GameSession
import com.example.braintrainercompose.game.domain.models.GameResults

class GameSessionAdapter {
    fun transformToGameSessionData(
        gameResults: GameResults,
        name: String,
        difficulty: String
    ): GameSession = GameSession(
        name = name,
        gameResults = gameResults,
        difficulty = difficulty
    )
}