package com.example.braintrainercompose.game.data.expression

import com.example.braintrainercompose.game.domain.api.ExpressionGeneratorRepo
import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.models.MathExpression

class ExpressionGeneratorRepoImpl(private val generator: GeneratorClient) :
    ExpressionGeneratorRepo {
    override fun generateExpression(gameSettings: GameSettings): List<MathExpression> =
        when (generator) {
            is GeneratorClientImpl -> generator.generate(gameSettings)
            else -> listOf()
        }
}