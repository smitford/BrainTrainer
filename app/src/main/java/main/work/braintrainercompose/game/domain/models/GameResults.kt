package main.work.braintrainercompose.game.domain.models

data class GameResults(
    val score: String = "",
    val time: String? = "00:00",
    val accuracy: Float = 100F
)
