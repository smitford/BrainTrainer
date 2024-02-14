package com.example.braintrainercompose.utils.data.dao

import com.example.braintrainercompose.game.data.models.GameSession
import com.example.braintrainercompose.scores.domain.models.SessionHistory

class DaoAdapter {
    fun fromScoreEntityToSessionHistory(scoresEntity: ScoresEntity): SessionHistory =
        SessionHistory(
            id = scoresEntity.id ?: 0,
            userName = scoresEntity.name,
            score = scoresEntity.score,
            difficulty = scoresEntity.difficulty
        )

    fun fromGameSessionToScoreEntity(gameSession: GameSession) =
        ScoresEntity(
            id = null,
            name = gameSession.name,
            score = gameSession.gameResults.score,
            difficulty = gameSession.difficulty
        )
}