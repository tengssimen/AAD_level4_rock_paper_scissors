package com.example.rockpaperscissors.models.enums

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.rockpaperscissors.R
import kotlin.random.Random

// implementing type-safe enums

enum class Move(val string: String) {
    ROCK("Rock"), PAPER("Paper"), SCISSORS("Scissors");

    override fun toString(): String{
        return string
    }

    fun getDrawable(context: Context): Drawable? {
        val drawableRef = when(this) {
            ROCK -> R.drawable.rock
            PAPER -> R.drawable.paper
            SCISSORS -> R.drawable.scissors
        }
        return ContextCompat.getDrawable(context, drawableRef)
    }

    companion object {
        fun getRandomMove(): Move {
            return values()[Random.nextInt(values().size)]
        }
    }
}