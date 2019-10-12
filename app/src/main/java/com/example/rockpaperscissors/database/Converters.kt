package com.example.rockpaperscissors.database

import android.util.Log
import androidx.room.TypeConverter
import com.example.rockpaperscissors.models.enums.Move
import com.example.rockpaperscissors.models.enums.Results
import java.util.*


class Converters
{
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    val tag_move = "MOVE_CONVERTER"

    @TypeConverter
    fun toMove(ordinal: Int): Move? {
        val res = Move.values().first { it.ordinal == ordinal }
        Log.i(tag_move, "[moveFromString] Converted $ordinal to $res")
        return res
    }

    @TypeConverter
    fun toOrdinal(move: Move): Int {
        val res = move.ordinal
        Log.i(tag_move, "[moveToString] Converted $move to $res")
        return res
    }

    val tag_result = "RESULT_CONVERTER"

    @TypeConverter
    fun toResult(ordinal: Int): Results? {
        val res = Results.values().first { it.ordinal == ordinal }
        Log.i(tag_result, "[resultFromString] Converted $ordinal to $res")
        return res
    }


    @TypeConverter
    fun toString(result: Results): Int {
        val res = result.ordinal
        Log.i(tag_result, "[resultToString] Converted $result to $res")
        return res
    }















}