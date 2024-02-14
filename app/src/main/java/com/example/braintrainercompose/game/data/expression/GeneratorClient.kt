package com.example.braintrainercompose.game.data.expression

import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.models.MathExpression
import com.example.braintrainercompose.game.domain.models.MathExpressions

interface GeneratorClient {
    fun generate(gameSettings: GameSettings): List<MathExpression>
}