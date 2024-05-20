package main.work.braintrainercompose.game.domain.use_case

interface GetTime {
    fun execute(currentTimer: Long, isCountDown: Boolean): String
}