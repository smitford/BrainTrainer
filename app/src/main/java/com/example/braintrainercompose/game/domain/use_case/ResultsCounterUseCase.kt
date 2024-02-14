package com.example.braintrainercompose.game.domain.use_case

import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.game.domain.models.GameSettings

interface ResultsCounterUseCase {
    fun execute(
        answerList: Map<Int, String>,
        expressionAnswers: List<Int>,
        gameSettings: GameSettings,
        time: Long
    ): GameResults
}