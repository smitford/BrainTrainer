package main.work.braintrainercompose.game.data.timer

import main.work.braintrainercompose.game.domain.api.TimerRepo
import main.work.braintrainercompose.utils.DataUtils.Companion.TIMER_DELAY_MSC
import main.work.braintrainercompose.utils.formatMaker

class TimerRepoImp : TimerRepo {
    override fun getTime(currentTime: Long, isCountDown: Boolean): String =
        if (isCountDown)
            formatMaker(time = currentTime + TIMER_DELAY_MSC)
        else
            formatMaker(time = currentTime + TIMER_DELAY_MSC)


}