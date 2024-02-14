package com.example.braintrainercompose.game.ui.models

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.models.MathExpression

data class GameState(
    val settings: GameSettings = GameSettings(),
    val answers: SnapshotStateMap<Int, String> = mutableStateMapOf(),
    val expression: List<MathExpression> = listOf(),
    val gapsFiled: Boolean = false,
    val gamesProgress: GameProgress = GameProgress.NOT_STARTED,
    val timer: String = "00:00",
    val gameResults: GameResults =GameResults()
)
