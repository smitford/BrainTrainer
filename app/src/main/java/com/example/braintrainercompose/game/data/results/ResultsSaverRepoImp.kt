package com.example.braintrainercompose.game.data.results

import android.content.SharedPreferences
import com.example.braintrainercompose.game.data.GameSessionAdapter
import com.example.braintrainercompose.game.domain.api.ResultsSaverRepo
import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.utils.DataUtils.Companion.GAME_SESSIONS
import com.google.gson.Gson

class ResultsSaverRepoImp(
    val gson: Gson,
    val sharedPref: SharedPreferences,
    val adapter: GameSessionAdapter
) : ResultsSaverRepo {
    override fun saveResults(results: GameResults, name: String, difficulty: String) {
        val gameSession = adapter.transformToGameSessionData(
            gameResults = results,
            name = name,
            difficulty = difficulty
        )
        sharedPref
            .edit()
            .putString(GAME_SESSIONS, gson.toJson(gameSession))
            .apply()
    }

}