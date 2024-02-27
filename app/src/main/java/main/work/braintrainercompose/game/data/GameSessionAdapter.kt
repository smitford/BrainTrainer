package main.work.braintrainercompose.game.data

import main.work.braintrainercompose.game.data.models.GameSession
import main.work.braintrainercompose.game.domain.models.GameResults

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