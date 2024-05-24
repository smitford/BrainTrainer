package main.work.braintrainercompose.scores.ui.models

import main.work.braintrainercompose.R
import main.work.braintrainercompose.utils.DataUtils.Companion.WEIGHT_0_1
import main.work.braintrainercompose.utils.DataUtils.Companion.WEIGHT_0_2
import main.work.braintrainercompose.utils.DataUtils.Companion.WEIGHT_0_25

sealed class TableTitle(val textId: Int, val weightNoTime: Float, val weightTime: Float) {
    data object AccuracyEng : TableTitle(
        textId = R.string.accuracy,
        weightNoTime = WEIGHT_0_1,
        weightTime = WEIGHT_0_1
    )


    data object NickNameEng : TableTitle(
        textId = R.string.nick_name,
        weightNoTime = WEIGHT_0_25,
        weightTime = WEIGHT_0_25
    )

    data object DifficultyEng : TableTitle(
        textId = R.string.difficulty,
        weightNoTime = WEIGHT_0_25,
        weightTime = WEIGHT_0_25
    )

    data object ScoreEng : TableTitle(
        textId = R.string.score,
        weightNoTime = WEIGHT_0_2,
        weightTime = WEIGHT_0_2
    )

    data object TimeEng : TableTitle(
        textId = R.string.time,
        weightNoTime = WEIGHT_0_2,
        weightTime = WEIGHT_0_2
    )

    data object NickNameRu : TableTitle(
        textId = R.string.nick_name,
        weightNoTime = WEIGHT_0_25,
        weightTime = WEIGHT_0_25
    )

    data object AccuracyRu : TableTitle(
        textId = R.string.accuracy,
        weightNoTime = WEIGHT_0_1,
        weightTime = WEIGHT_0_1
    )

    data object DifficultyRu : TableTitle(
        textId = R.string.difficulty,
        weightNoTime = WEIGHT_0_25,
        weightTime = WEIGHT_0_25
    )

    data object ScoreRu : TableTitle(
        textId = R.string.score,
        weightNoTime = WEIGHT_0_2,
        weightTime = WEIGHT_0_2
    )

    data object TimeRu : TableTitle(
        textId = R.string.time,
        weightNoTime = WEIGHT_0_25,
        weightTime = WEIGHT_0_25
    )

}