package com.example.rockpaperscissors.database

import android.content.Context
import com.example.rockpaperscissors.models.data.Statistics
import com.example.rockpaperscissors.models.entity.Game
import com.example.rockpaperscissors.models.enums.Results

class GameRepository(context: Context) {

    private var gameDao: GameDao

    init {
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = gameRoomDatabase!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    suspend fun deleteAllGames(){
        gameDao.deleteAllGames()
    }

    suspend fun getStatistics(): Statistics {
        val won = gameDao.getGameCount(Results.WIN)
        val lost = gameDao.getGameCount(Results.LOSS)
        val draw = gameDao.getGameCount(Results.DRAW)
        return Statistics(won, lost, draw)
    }

}