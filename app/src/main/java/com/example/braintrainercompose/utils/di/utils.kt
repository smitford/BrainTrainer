package com.example.braintrainercompose.utils.di

import androidx.room.Room
import com.example.braintrainercompose.utils.data.dao.AppDataBase
import com.example.braintrainercompose.utils.data.dao.DaoAdapter
import com.example.braintrainercompose.utils.data.dao.DataBaseRepositoryImp
import com.example.braintrainercompose.utils.domain.api.DataBaseRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utils = module {
    single { DaoAdapter() }
    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "brainTrainer.db")
            .build()
    }
    single<DataBaseRepository> { DataBaseRepositoryImp(dataBase = get(), adapter = get()) }
}