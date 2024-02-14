package com.example.braintrainercompose.game.domain.use_case

import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.models.MathExpression
import com.example.braintrainercompose.game.domain.models.MathExpressions


interface GetExpression {
    fun execute(gameSettings: GameSettings): List<MathExpression>
}