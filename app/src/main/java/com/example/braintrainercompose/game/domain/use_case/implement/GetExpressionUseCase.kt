package com.example.braintrainercompose.game.domain.use_case.implement

import com.example.braintrainercompose.game.domain.api.ExpressionGeneratorRepo
import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.models.MathExpression
import com.example.braintrainercompose.game.domain.use_case.GetExpression

class GetExpressionUseCase(private val expressionGenerator: ExpressionGeneratorRepo): GetExpression {
    override fun execute(gameSettings: GameSettings): List<MathExpression> =
        expressionGenerator.generateExpression(gameSettings)

}