package main.work.braintrainercompose.utils.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

/*@Database(
    version = 1,
    entities = [ScoresEntity::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}*/

@Database(
    version = 2,
    entities = [ScoresEntity::class],
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

}




