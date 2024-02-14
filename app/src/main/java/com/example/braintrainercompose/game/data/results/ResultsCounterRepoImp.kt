package com.example.braintrainercompose.game.data.results

import com.example.braintrainercompose.game.domain.api.ResultsCounterRepo
import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.utils.DataUtils.Companion.REDUCTION_FACTOR
import com.example.braintrainercompose.utils.formatMaker
import kotlin.math.round

class ResultsCounterRepoImp : ResultsCounterRepo {
    override fun count(
        answerList: Map<Int, String>,
        expressionAnswers: List<Int>,
        gameSettings: GameSettings,
        time: Long
    ): GameResults {
        val baseTime = gameSettings.difficulty.time * gameSettings.difficulty.count
        val rightAnswers = rightAnswersCalculation(answerList, expressionAnswers)
        val score = scoreFormula(
            calculationDifCoef = gameSettings.expressionType.calculationDifCoef.toFloat(),
            scoreCoef = gameSettings.difficulty.scoreCoef.toFloat(),
            baseTime = baseTime.toFloat(),
            time = time.toFloat(),
            expressionCount = gameSettings.difficulty.count.toFloat(),
            rightAnswers = rightAnswers.toFloat(),
            isTimeGame = gameSettings.timeGame
        )
        val countingTime =
            if (gameSettings.countDown)
                baseTime - time
            else
                time
        return GameResults(score = score, formatMaker(countingTime))
    }

    private fun scoreFormula(
        calculationDifCoef: Float,
        scoreCoef: Float,
        baseTime: Float,
        time: Float,
        expressionCount: Float,
        rightAnswers: Float,
        isTimeGame: Boolean
    ): String {
        return if (isTimeGame) {
            round(calculationDifCoef * ((baseTime / (time / 1000)) * (rightAnswers / expressionCount) * scoreCoef)).toString()
        } else {
            round(calculationDifCoef * REDUCTION_FACTOR * (rightAnswers / expressionCount) * scoreCoef).toString()

        }
    }

    private fun rightAnswersCalculation(
        answerList: Map<Int, String>,
        expressionAnswers: List<Int>
    ): Int =
        expressionAnswers.mapIndexed { index, i -> i.toString() == answerList[index] }
            .count { it }

}