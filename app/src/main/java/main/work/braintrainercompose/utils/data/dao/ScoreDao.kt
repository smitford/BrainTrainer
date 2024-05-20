package main.work.braintrainercompose.utils.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {
    @Insert
    fun insertSessionScore(sessionScore: ScoresEntity)

    @Query("SELECT * FROM session_score ORDER BY score DESC")
    fun getAllSessionsScores(): List<ScoresEntity>

    @Query("DELETE FROM session_score")
    fun deleteAllSessions()
}