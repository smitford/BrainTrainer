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

    @Query("SELECT * FROM session_score WHERE game_type IS 'FREE_GAME' ORDER BY score DESC")
    fun getAllNotTimedHistory(): List<ScoresEntity>

    @Query("SELECT * FROM session_score WHERE game_type IS 'TIME_GAME' ORDER BY score DESC")
    fun getAllTimedHistory(): List<ScoresEntity>

    @Query("SELECT * FROM session_score WHERE game_type IS 'TIME_RACE' ORDER BY score DESC")
    fun getAllTimeRacedHistory(): List<ScoresEntity>


    @Query("DELETE FROM session_score")
    fun deleteAllSessions()
}