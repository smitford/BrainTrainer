package com.example.braintrainercompose.game.di

import android.app.Application
import com.example.braintrainercompose.game.data.GameSessionAdapter
import com.example.braintrainercompose.game.data.expression.ExpressionGeneratorRepoImpl
import com.example.braintrainercompose.game.data.expression.GeneratorClient
import com.example.braintrainercompose.game.data.expression.GeneratorClientImpl
import com.example.braintrainercompose.game.data.results.ResultsCounterRepoImp
import com.example.braintrainercompose.game.data.settings.SettingsSharedPref
import com.example.braintrainercompose.game.data.timer.TimerRepoImp
import com.example.braintrainercompose.game.domain.api.ExpressionGeneratorRepo
import com.example.braintrainercompose.game.domain.api.ResultsCounterRepo
import com.example.braintrainercompose.game.domain.api.SettingsRepo
import com.example.braintrainercompose.game.domain.api.TimerRepo
import com.example.braintrainercompose.game.domain.use_case.GetExpression
import com.example.braintrainercompose.game.domain.use_case.GetTime
import com.example.braintrainercompose.game.domain.use_case.ResultSaverUseCase
import com.example.braintrainercompose.game.domain.use_case.ResultsCounterUseCase
import com.example.braintrainercompose.game.domain.use_case.SettingsInteractor
import com.example.braintrainercompose.game.domain.use_case.implement.GetExpressionUseCase
import com.example.braintrainercompose.game.domain.use_case.implement.GetTimeUseCase
import com.example.braintrainercompose.game.domain.use_case.implement.ResultSaverUseCaseImp
import com.example.braintrainercompose.game.domain.use_case.implement.ResultsCounterUseCaseImp
import com.example.braintrainercompose.game.domain.use_case.implement.SettingsInteractorImp
import com.example.braintrainercompose.game.ui.view_model.GameViewModel
import com.example.braintrainercompose.utils.DataUtils.Companion.APP_SETTINGS
import com.google.gson.Gson
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
    viewModel {
        GameViewModel(
            expressionGetter = get(),
            settingsInteractor = get(),
            timeGetter = get(),
            resultsCounter = get(),
            resultSaver = get()
        )
    }
    single { Gson() }
    single {
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