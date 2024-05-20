package main.work.braintrainercompose.game.domain.api

interface TimerRepo {
    fun getTime(currentTime: Long, isCountDown:Boolean): String
}