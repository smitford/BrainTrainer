package main.work.braintrainercompose.game.data.expression

import main.work.braintrainercompose.game.domain.models.DifficultyLevels
import main.work.braintrainercompose.game.domain.models.ExpressionType
import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.models.MathExpression
import kotlin.random.Random

class GeneratorClientImpl : GeneratorClient {
    override fun generate(gameSettings: GameSettings): List<MathExpression> =
        generateList(
            type = gameSettings.expressionType,
            gameSettings.difficulty
        )


    private fun generateList(
        type: ExpressionType,
        difficulty: DifficultyLevels
    ): List<MathExpression> {
        val randomFirst = generateRandomList(count = difficulty.count, MAX_UNTIL)
        val randomSecond =
            generateRandomList(
                count = difficulty.count,
                when (type) {
                    ExpressionType.DIVISION, ExpressionType.MULTIPLICATION -> MIN_UNTIL
                    else -> MAX_UNTIL
                }
            )
        return when (type) {
            ExpressionType.DIVISION ->
                randomFirst.mapIndexed { index, i ->
                    MathExpression(
                        id = index,
                        firstNumber = randomSecond[index] * i,
                        secondNumber = randomSecond[index],
                        answer = i
                    )
                }

            else -> randomFirst.mapIndexed { index, i ->
                MathExpression(
                    id = index,
                    firstNumber = i,
                    secondNumber = randomSecond[index],
                    answer = type.execute(i, randomSecond[index])
                )
            }
        }
    }

    private fun generateRandomList(count: Int, numberUntil: Int) =
        List(count) { Random.nextInt(from = FROM, until = numberUntil) }

    companion object {
        const val MAX_UNTIL = 99
        const val MIN_UNTIL = 9
        const val FROM = 1
    }
}