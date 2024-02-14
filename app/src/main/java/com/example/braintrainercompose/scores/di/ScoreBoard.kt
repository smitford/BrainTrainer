package com.example.braintrainercompose.scores.di

import com.example.braintrainercompose.scores.domain.use_case.ClearHistoryUseCase
import com.example.braintrainercompose.scores.domain.use_case.GetHistoryUseCase
import com.example.braintrainercompose.scores.domain.use_case.implementations.ClearHistoryUseCaseImp
import com.example.braintrainercompose.scores.domain.use_case.implementations.GetHistoryUseCaseImp
import com.example.braintrainercompose.scores.ui.view_model.ScoreBoard
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val scoreBoard = module {
    viewModel {
        ScoreBoard(getDataUseCase = get(), clearHistoryUseCase = get())
    }
    factory<ClearHistoryUseCase> { ClearHistoryUseCaseImp(repository = get()) }
    factory<GetHistoryUseCase> { GetHistoryUseCaseImp(repository = get()) }
}