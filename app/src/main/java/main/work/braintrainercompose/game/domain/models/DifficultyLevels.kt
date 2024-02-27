package main.work.braintrainercompose.game.domain.models

import main.work.braintrainercompose.R
import main.work.braintrainercompose.utils.DataUtils.Companion.EASY_COUNT_EXPRESSION
import main.work.braintrainercompose.utils.DataUtils.Companion.EASY_TIME_SEC
import main.work.braintrainercompose.utils.DataUtils.Companion.HARD_COUNT_EXPRESSION
import main.work.braintrainercompose.utils.DataUtils.Companion.HARD_TIME_SEC
import main.work.braintrainercompose.utils.DataUtils.Companion.IMPOSSIBLE_COUNT_EXPRESSION
import main.work.braintrainercompose.utils.DataUtils.Companion.IMPOSSIBLE_TIME_SEC
import main.work.braintrainercompose.utils.DataUtils.Companion.NORMAL_COUNT_EXPRESSION
import main.work.braintrainercompose.utils.DataUtils.Companion.NORMAL_TIME_SEC
import main.work.braintrainercompose.utils.DataUtils.Companion.SCORE_COEFFICIENT_EASY
import main.work.braintrainercompose.utils.DataUtils.Companion.SCORE_COEFFICIENT_HARD
import main.work.braintrainercompose.utils.DataUtils.Companion.SCORE_COEFFICIENT_IMPOSSIBLE
import main.work.braintrainercompose.utils.DataUtils.Companion.SCORE_COEFFICIENT_NORMAL

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
