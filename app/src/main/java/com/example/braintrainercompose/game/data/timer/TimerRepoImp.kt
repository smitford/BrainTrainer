package com.example.braintrainercompose.game.data.timer

import com.example.braintrainercompose.game.domain.api.TimerRepo
import com.example.braintrainercompose.utils.DataUtils.Companion.TIMER_DELAY_MSC
import com.example.braintrainercompose.utils.formatMaker

class TimerRepoImp : TimerRepo {
    override fun getTime(currentTime: Long, isCountDown: Boolean): String =
        if (isCountDown)
            formatMaker(time = currentTime + TIMER_DELAY_MSC)
        else
            formatMaker(time = currentTime + TIMER_DELAY_MSC)


}