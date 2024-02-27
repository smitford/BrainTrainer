package main.work.braintrainercompose.game.domain.models

data class GameSettings(
    val difficulty: DifficultyLevels = DifficultyLevels.NORMAL,
    val timeGame: Boolean = true,
    val countDown: Boolean = false,
    val scoreCount: Boolean = false,
    val expressionType: ExpressionType = ExpressionType.ADDITION
)