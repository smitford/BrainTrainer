package main.work.braintrainercompose.game.domain.api

import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.models.MathExpression

interface ExpressionGeneratorRepo {
    fun generateExpression(gameSettings: GameSettings): List<MathExpression>
}