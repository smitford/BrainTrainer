package main.work.braintrainercompose.scores.domain.models

import main.work.braintrainercompose.utils.domain.models.GameType

data class SessionHistory(
    val id: Int,
    val userName: String,
    val score: String,
    val difficulty: String,
    val accuracy: Float,
    val time: String?,
    val gameType: GameType
)


