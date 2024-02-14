package com.example.braintrainercompose.scores.ui.models

import com.example.braintrainercompose.scores.domain.models.SessionHistory

data class ScoreBoardStatus(
    val isEmpty: Boolean = true,
    val sessionHistoryList: List<SessionHistory>? = null
)