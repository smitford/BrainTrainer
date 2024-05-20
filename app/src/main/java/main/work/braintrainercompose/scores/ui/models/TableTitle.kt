package main.work.braintrainercompose.scores.ui.models

import main.work.braintrainercompose.R

sealed class TableTitle(val textId: Int, val weightNoTime: Float, val weightTime: Float) {
    data object Accuracy : TableTitle(
        textId = R.string.accuracy,
        weightNoTime = 0.1f,
        weightTime = 0.1f
    )

    data object NickName : TableTitle(
        textId = R.string.nick_name,
        weightNoTime = 0.25f,
        weightTime = 0.25f
    )

    data object Difficulty : TableTitle(
        textId = R.string.difficulty,
        weightNoTime = 0.25f,
        weightTime = 0.25f
    )

    data object Score : TableTitle(
        textId = R.string.score,
        weightNoTime = 0.2f,
        weightTime = 0.2f
    )

    data object Time : TableTitle(
        textId = R.string.time,
        weightNoTime = 0.2f,
        weightTime = 0.2f
    )

}