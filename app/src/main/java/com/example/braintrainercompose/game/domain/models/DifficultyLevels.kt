package com.example.braintrainercompose.game.domain.models

import com.example.braintrainercompose.R
import com.example.braintrainercompose.utils.DataUtils.Companion.EASY_COUNT_EXPRESSION
import com.example.braintrainercompose.utils.DataUtils.Companion.EASY_TIME_SEC
import com.example.braintrainercompose.utils.DataUtils.Companion.HARD_COUNT_EXPRESSION
import com.example.braintrainercompose.utils.DataUtils.Companion.HARD_TIME_SEC
import com.example.braintrainercompose.utils.DataUtils.Companion.IMPOSSIBLE_COUNT_EXPRESSION
import com.example.braintrainercompose.utils.DataUtils.Companion.IMPOSSIBLE_TIME_SEC
import com.example.braintrainercompose.utils.DataUtils.Companion.NORMAL_COUNT_EXPRESSION
import com.example.braintrainercompose.utils.DataUtils.Companion.NORMAL_TIME_SEC
import com.example.braintrainercompose.utils.DataUtils.Companion.SCORE_COEFFICIENT_EASY
import com.example.braintrainercompose.utils.DataUtils.Companion.SCORE_COEFFICIENT_HARD
import com.example.braintrainercompose.utils.DataUtils.Companion.SCORE_COEFFICIENT_IMPOSSIBLE
import com.example.braintrainercompose.utils.DataUtils.Companion.SCORE_COEFFICIENT_NORMAL

enum class DifficultyLevels(val id: Int, val count: Int, val time: Int,val scoreCoef: Int) {
    EASY(
        id = R.string.easy,
        count = EASY_COUNT_EXPRESSION,
        time = EASY_TIME_SEC,
        scoreCoef = SCORE_COEFFICIENT_EASY
    ),
    NORMAL(
        id = R.string.normal,
        count = NORMAL_COUNT_EXPRESSION,
        time = NORMAL_TIME_SEC,
        scoreCoef = SCORE_COEFFICIENT_NORMAL
    ),
    HARD(
        id = R.string.hard,
        count = HARD_COUNT_EXPRESSION,
        time = HARD_TIME_SEC,
        scoreCoef = SCORE_COEFFICIENT_HARD
    ),
    IMPOSSIBLE(
        id = R.string.impossible,
        count = IMPOSSIBLE_COUNT_EXPRESSION,
        time = IMPOSSIBLE_TIME_SEC,
        scoreCoef = SCORE_COEFFICIENT_IMPOSSIBLE
    )

}
