package main.work.braintrainercompose.game.domain.use_case

import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.models.MathExpression


interface GetExpression {
    fun execute(gameSettings: GameSettings): List<MathExpression>
}