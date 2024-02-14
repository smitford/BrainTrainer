package com.example.braintrainercompose.game.domain.api

import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.models.MathExpression

interface ExpressionGeneratorRepo {
    fun generateExpression(gameSettings: GameSettings): List<MathExpression>
}