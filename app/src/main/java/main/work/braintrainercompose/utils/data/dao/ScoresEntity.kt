package main.work.braintrainercompose.utils.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_score")
data class ScoresEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val score: String,
    val difficulty: String,
    @ColumnInfo(defaultValue = "100")
    val accuracy: Float,
    val time: String?,
    @ColumnInfo(defaultValue = "FREE_GAME", name = "game_type")
    val gameType: String
)
