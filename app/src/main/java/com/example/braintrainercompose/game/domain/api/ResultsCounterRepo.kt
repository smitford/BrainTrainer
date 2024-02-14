package com.example.braintrainercompose.game.domain.api

import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.game.domain.models.GameSettings

interface ResultsCounterRepo {
    fun count(
        answerList: Map<Int, String>,
        expressionAnswers: List<Int>,
        gameSettings: GameSettings,
        time: Long
    ): GameResults
}