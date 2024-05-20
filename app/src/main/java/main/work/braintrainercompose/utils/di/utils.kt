package main.work.braintrainercompose.utils.di

import androidx.room.Room
import main.work.braintrainercompose.utils.data.dao.AppDataBase
import main.work.braintrainercompose.utils.data.dao.DaoAdapter
import main.work.braintrainercompose.utils.data.dao.DataBaseRepositoryImp
import main.work.braintrainercompose.utils.domain.api.DataBaseRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utils = module {
    single { DaoAdapter() }
    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "brainTrainer.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single<DataBaseRepository> { DataBaseRepositoryImp(dataBase = get(), adapter = get()) }
}