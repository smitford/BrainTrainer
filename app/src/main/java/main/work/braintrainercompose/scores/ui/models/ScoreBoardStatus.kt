package main.work.braintrainercompose.scores.ui.models

import main.work.braintrainercompose.scores.domain.models.SessionHistory

data class ScoreBoardStatus(
    val isEmpty: Boolean = true,
    val sessionHistoryList: List<SessionHistory>? = null
)