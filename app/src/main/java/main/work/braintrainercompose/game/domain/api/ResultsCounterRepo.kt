package main.work.braintrainercompose.game.domain.api

import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.game.domain.models.GameSettings

interface ResultsCounterRepo {
    fun count(
        answerList: Map<Int, String>,
        expressionAnswers: List<Int>,
        gameSettings: GameSettings,
        time: Long
    ): GameResults
}