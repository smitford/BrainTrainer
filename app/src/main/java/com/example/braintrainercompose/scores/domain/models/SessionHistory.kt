package com.example.braintrainercompose.scores.domain.models

data class SessionHistory(
    val id: Int,
    val userName: String,
    val score: String,
    val difficulty: String
)
