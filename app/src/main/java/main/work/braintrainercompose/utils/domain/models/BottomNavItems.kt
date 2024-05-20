package main.work.braintrainercompose.utils.domain.models

import main.work.braintrainercompose.R

sealed class BottomNavItems(
    val title: String,
    val icon: Int
) {
    data object GamePlay : BottomNavItems(
        title = "Play",
        icon = R.drawable.play_circle_filled
    )

    data object Settings : BottomNavItems(
        title = "Settings",
        icon = R.drawable.settings
    )

    data object ScoreBoard : BottomNavItems(
        title = "Score Board",
        icon = R.drawable.raiting
    )

    data object GamePause : BottomNavItems(
        title = "Play",
        icon = R.drawable.pause_circle
    )

    data object GameRestart : BottomNavItems(
        title = "Play",
        icon = R.drawable.replay_circle
    )

}