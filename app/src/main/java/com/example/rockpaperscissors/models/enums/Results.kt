package com.example.rockpaperscissors.models.enums

enum class Results(val string: String) {
    WIN("Win"), LOSS("Loss"), DRAW("Draw");

    override fun toString(): String {
        return string
    }
}