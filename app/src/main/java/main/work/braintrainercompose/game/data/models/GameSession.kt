package main.work.braintrainercompose.game.data.models

import main.work.braintrainercompose.game.domain.models.GameResults

data class GameSession(val name: String, val gameResults: GameResults, val difficulty: String)
