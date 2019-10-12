package com.example.rockpaperscissors.database

import androidx.room.*
import com.example.rockpaperscissors.models.entity.Game
import com.example.rockpaperscissors.models.enums.Results

@Dao
interface GameDao {
    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(*) FROM game_table WHERE result == :result")
    suspend fun getGameCount(result: Results): Int
}