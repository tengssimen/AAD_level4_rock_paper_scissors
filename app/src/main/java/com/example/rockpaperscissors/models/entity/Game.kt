package com.example.rockpaperscissors.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rockpaperscissors.models.enums.Move
import com.example.rockpaperscissors.models.enums.Results
import java.util.*

@Entity(tableName = "game_table")
data class Game(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "result")
    var result: Results,

    @ColumnInfo(name = "pcMove")
    var pcMove: Move,

    @ColumnInfo(name = "userMove")
    var userMove: Move
) {
    override fun toString(): String {
        return "Game(id=$id, date=$date, result=$result, pcMove=$pcMove, userMove=$userMove)"
    }
}