package main.work.braintrainercompose.scores.ui.models

import main.work.braintrainercompose.scores.domain.models.GamesHistory

data class ScoreBoardStatus(
    val isEmpty: Boolean = true,
    val sessionHistoryList: GamesHistory? = null
)