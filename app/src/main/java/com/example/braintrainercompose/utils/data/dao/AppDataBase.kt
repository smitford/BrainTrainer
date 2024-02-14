package com.example.braintrainercompose.utils.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [ScoresEntity::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}