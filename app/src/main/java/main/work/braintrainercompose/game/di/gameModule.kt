package main.work.braintrainercompose.game.di

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.Gson
import main.work.braintrainercompose.game.data.GameSessionAdapter
import main.work.braintrainercompose.game.data.expression.ExpressionGeneratorRepoImpl
import main.work.braintrainercompose.game.data.expression.GeneratorClient
import main.work.braintrainercompose.game.data.expression.GeneratorClientImpl
import main.work.braintrainercompose.game.data.results.ResultsCounterRepoImp
import main.work.braintrainercompose.game.data.settings.SettingsSharedPref
import main.work.braintrainercompose.game.data.timer.TimerRepoImp
import main.work.braintrainercompose.game.domain.api.ExpressionGeneratorRepo
import main.work.braintrainercompose.game.domain.api.ResultsCounterRepo
import main.work.braintrainercompose.game.domain.api.SettingsRepo
import main.work.braintrainercompose.game.domain.api.TimerRepo
import main.work.braintrainercompose.game.domain.use_case.GetExpression
import main.work.braintrainercompose.game.domain.use_case.GetTime
import main.work.braintrainercompose.game.domain.use_case.ResultSaverUseCase
import main.work.braintrainercompose.game.domain.use_case.ResultsCounterUseCase
import main.work.braintrainercompose.game.domain.use_case.SettingsInteractor
import main.work.braintrainercompose.game.domain.use_case.implement.GetExpressionUseCase
import main.work.braintrainercompose.game.domain.use_case.implement.GetTimeUseCase
import main.work.braintrainercompose.game.domain.use_case.implement.ResultSaverUseCaseImp
import main.work.braintrainercompose.game.domain.use_case.implement.ResultsCounterUseCaseImp
import main.work.braintrainercompose.game.domain.use_case.implement.SettingsInteractorImp
import main.work.braintrainercompose.game.ui.view_model.GameViewModel
import main.work.braintrainercompose.utils.DataUtils.Companion.APP_SETTINGS
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val gameModule = module {
    single<GeneratorClient> {
        GeneratorClientImpl()
    }
    single<ExpressionGeneratorRepo> {
        ExpressionGeneratorRepoImpl(generator = get())
    }
    factory<GetExpression> {
        GetExpressionUseCase(expressionGenerator = get())
    }
    viewModel {parameters->
        GameViewModel(
            expressionGetter = get(),
            settingsInteractor = get(),
            timeGetter = get(),
            resultsCounter = get(),
            resultSaver = get(),
            bottomBarSynchronizer = parameters.get()
        )
    }

    single { Gson() }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            APP_SETTINGS, Application.MODE_PRIVATE
        )
    }
    single<SettingsRepo> {
        SettingsSharedPref(sharedPref = get(), gson = get())
    }
    factory<SettingsInteractor> {
        SettingsInteractorImp(repository = get())
    }
    single<TimerRepo> { TimerRepoImp() }

    factory<GetTime> { GetTimeUseCase(timerRepo = get()) }

    single<ResultsCounterRepo> { ResultsCounterRepoImp() }

    factory<ResultsCounterUseCase> { ResultsCounterUseCaseImp(repository = get()) }

    factory<ResultSaverUseCase> { ResultSaverUseCaseImp(repository = get()) }

    single { GameSessionAdapter() }

}