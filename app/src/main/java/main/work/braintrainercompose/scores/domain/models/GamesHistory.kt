package main.work.braintrainercompose.scores.domain.models

data class GamesHistory(
    val freeGamesHistory: List<SessionHistory>,
    val timedGamesHistory: List<SessionHistory>,
    val timeRaceGamesHistory: List<SessionHistory>
)