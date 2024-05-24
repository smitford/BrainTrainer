package main.work.braintrainercompose.game.data.models

import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.utils.domain.models.GameType

data class GameSession(
    val name: String,
    val gameResults: GameResults,
    val difficulty: String,
    val gameType: GameType
)
