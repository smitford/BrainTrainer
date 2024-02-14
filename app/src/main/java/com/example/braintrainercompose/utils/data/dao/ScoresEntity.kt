package com.example.braintrainercompose.utils.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_score")
data class ScoresEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val score: String,
    val difficulty: String
)
