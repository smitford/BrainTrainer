package main.work.braintrainercompose.game.data.expression

import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.models.MathExpression

interface GeneratorClient {
    fun generate(gameSettings: GameSettings): List<MathExpression>
}