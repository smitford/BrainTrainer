package main.work.braintrainercompose.scores.ui.models

import main.work.braintrainercompose.R

enum class TableTitleElement(val textId: Int, val weight: Float) {
    NUMBER(textId = R.string.number, weight = 0.05f),
    NICK_NAME(textId = R.string.nick_name, weight = 0.35f),
    DIFFICULTY(textId = R.string.difficulty, weight = 0.35f),
    SCORE(textId = R.string.score, weight = 0.2f)
}