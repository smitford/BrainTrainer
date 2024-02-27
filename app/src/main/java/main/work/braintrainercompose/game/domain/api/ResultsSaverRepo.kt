package main.work.braintrainercompose.game.domain.api

import main.work.braintrainercompose.game.domain.models.GameResults

interface ResultsSaverRepo {
    fun saveResults(results: GameResults, name: String, difficulty: String)
}