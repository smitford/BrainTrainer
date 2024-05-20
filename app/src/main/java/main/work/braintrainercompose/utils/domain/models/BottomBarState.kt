package main.work.braintrainercompose.utils.domain.models

enum class BottomBarState(val bottomBarElements: List<BottomNavItems>) {
    PLAY_ICON(
        bottomBarElements = listOf(
            BottomNavItems.ScoreBoard,
            BottomNavItems.GamePlay,
            BottomNavItems.Settings
        )
    ),
    PAUSE_ICON(
        bottomBarElements = listOf(
            BottomNavItems.ScoreBoard,
            BottomNavItems.GamePause,
            BottomNavItems.Settings
        )
    ),
    RESTART_ICON(
        bottomBarElements = listOf(
            BottomNavItems.ScoreBoard,
            BottomNavItems.GameRestart,
            BottomNavItems.Settings
        )
    )
}