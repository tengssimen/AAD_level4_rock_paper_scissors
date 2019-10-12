package com.example.rockpaperscissors.database

import android.content.Context
import androidx.room.*
import com.example.rockpaperscissors.models.entity.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameRoomDatabase : RoomDatabase() {

    //creates a cache of the app's data on the device that's running your app

    abstract fun gameDao(): GameDao


    //Can be called without a class instance

    companion object {
        private const val DATABASE_NAME = "DATABASE"

        @Volatile
        private var gameRoomDatabaseInstance: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase? {
            if (gameRoomDatabaseInstance == null) {
                synchronized(GameRoomDatabase::class.java) {
                    if (gameRoomDatabaseInstance == null) {
                        gameRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            GameRoomDatabase::class.java, DATABASE_NAME
                        ).build()
                    }
                }
            }
            return gameRoomDatabaseInstance
        }
    }
}