package main.work.braintrainercompose.utils.data.dao

import main.work.braintrainercompose.R
import main.work.braintrainercompose.game.data.models.GameSession
import main.work.braintrainercompose.game.domain.models.DifficultyLevels
import main.work.braintrainercompose.scores.domain.models.SessionHistory
import main.work.braintrainercompose.utils.domain.models.GameType

class DaoAdapter {
    fun fromScoreEntityToSessionHistory(scoresEntity: ScoresEntity): SessionHistory =
        SessionHistory(
            id = scoresEntity.id ?: 0,
            userName = scoresEntity.name,
            score = scoresEntity.score,
            difficulty = when (scoresEntity.difficulty) {
                DifficultyLevels.EASY.name -> R.drawable.c.toString()
                DifficultyLevels.HARD.name -> R.drawable.a.toString()
                DifficultyLevels.IMPOSSIBLE.name -> R.drawable.s.toString()
                DifficultyLevels.NORMAL.name -> R.drawable.b.toString()
                else -> R.drawable.criss_cross.toString()
            },
            accuracy = scoresEntity.accuracy,
            time = scoresEntity.time,
            gameType = GameType.valueOf(scoresEntity.gameType)
        )


    fun fromGameSessionToScoreEntity(gameSession: GameSession) =
        ScoresEntity(
            id = null,
            name = gameSession.name,
            score = gameSession.gameResults.score,
            difficulty = gameSession.difficulty,
            accuracy = gameSession.gameResults.accuracy.toString(),
            time = gameSession.gameResults.time,
            gameType = gameSession.gameType.name
        )
}