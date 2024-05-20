package main.work.braintrainercompose.game.data.expression

import main.work.braintrainercompose.game.domain.api.ExpressionGeneratorRepo
import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.models.MathExpression

class ExpressionGeneratorRepoImpl(private val generator: GeneratorClient) :
    ExpressionGeneratorRepo {
    override fun generateExpression(gameSettings: GameSettings): List<MathExpression> =
        when (generator) {
            is GeneratorClientImpl -> generator.generate(gameSettings)
            else -> listOf()
        }
}