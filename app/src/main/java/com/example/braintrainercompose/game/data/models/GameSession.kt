package com.example.braintrainercompose.game.data.models

import com.example.braintrainercompose.game.domain.models.GameResults

data class GameSession(val name: String, val gameResults: GameResults, val difficulty: String)
