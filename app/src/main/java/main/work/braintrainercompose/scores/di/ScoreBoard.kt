package main.work.braintrainercompose.scores.di

import main.work.braintrainercompose.scores.domain.use_case.ClearHistoryUseCase
import main.work.braintrainercompose.scores.domain.use_case.GetHistoryUseCase
import main.work.braintrainercompose.scores.domain.use_case.implementations.ClearHistoryUseCaseImp
import main.work.braintrainercompose.scores.domain.use_case.implementations.GetHistoryUseCaseImp
import main.work.braintrainercompose.scores.ui.view_model.ScoreBoard
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val scoreBoard = module {
    viewModel {
        ScoreBoard(getDataUseCase = get(), clearHistoryUseCase = get())
    }
    factory<ClearHistoryUseCase> { ClearHistoryUseCaseImp(repository = get()) }
    factory<GetHistoryUseCase> { GetHistoryUseCaseImp(repository = get()) }
}