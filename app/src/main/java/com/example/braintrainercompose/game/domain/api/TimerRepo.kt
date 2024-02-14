package com.example.braintrainercompose.game.domain.api

interface TimerRepo {
    fun getTime(currentTime: Long, isCountDown:Boolean): String
}