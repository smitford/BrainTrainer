package main.work.braintrainercompose.game.domain.use_case

import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.game.domain.models.GameSettings

interface ResultsCounterUseCase {
    fun execute(
        answerList: Map<Int, String>,
        expressionAnswers: List<Int>,
        gameSettings: GameSettings,
        time: Long
    ): GameResults
}