package main.work.braintrainercompose.game.domain.models

import main.work.braintrainercompose.utils.domain.models.GameType

class GameSettings(
    val difficulty: DifficultyLevels = DifficultyLevels.NORMAL,
    val timeGame: Boolean = true,
    val countDown: Boolean = false,
    val scoreCount: Boolean = false,
    val expressionType: ExpressionType = ExpressionType.ADDITION
) {
    fun getGameType(): GameType {
        return if (countDown)
            GameType.TIME_RACE
        else
            if (timeGame)
                GameType.TIME_GAME
            else
                GameType.FREE_GAME
    }
}