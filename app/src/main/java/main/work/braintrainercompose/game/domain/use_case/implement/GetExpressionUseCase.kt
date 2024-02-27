package main.work.braintrainercompose.game.domain.use_case.implement

import main.work.braintrainercompose.game.domain.api.ExpressionGeneratorRepo
import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.models.MathExpression
import main.work.braintrainercompose.game.domain.use_case.GetExpression

class GetExpressionUseCase(private val expressionGenerator: ExpressionGeneratorRepo): GetExpression {
    override fun execute(gameSettings: GameSettings): List<MathExpression> =
        expressionGenerator.generateExpression(gameSettings)

}