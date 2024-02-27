package main.work.braintrainercompose.utils.data.dao

import main.work.braintrainercompose.game.data.models.GameSession
import main.work.braintrainercompose.scores.domain.models.SessionHistory

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