package main.work.braintrainercompose.scores.ui.models

import main.work.braintrainercompose.R

sealed class TableTabs(val id: Int) {
    data object FreeCount : TableTabs(id = R.string.free_count)
    data object TimeCount : TableTabs(id = R.string.time_count)
    data object FastCount : TableTabs(id = R.string.time_race)
}

val tableTabsRow = listOf(
    TableTabs.FreeCount,
    TableTabs.TimeCount,
    TableTabs.FastCount
)