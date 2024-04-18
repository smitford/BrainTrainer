package main.work.braintrainercompose.utils

import android.app.Application
import main.work.braintrainercompose.game.di.gameModule
import main.work.braintrainercompose.scores.di.scoreBoard
import main.work.braintrainercompose.settings.di.settingsModule
import main.work.braintrainercompose.utils.di.utils
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BrainTrainerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BrainTrainerApp)
            modules(gameModule, utils, scoreBoard, settingsModule)
        }
    }
}