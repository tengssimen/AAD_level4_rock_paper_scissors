package com.example.rockpaperscissors.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.GameRepository
import com.example.rockpaperscissors.models.data.Statistics
import com.example.rockpaperscissors.models.entity.Game
import com.example.rockpaperscissors.models.enums.Move
import com.example.rockpaperscissors.models.enums.Results
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class GameActivity : AppCompatActivity() {
    private lateinit var gameRepository: GameRepository
    private var statistics: Statistics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Rock, Paper, Scissors, Kotlin"

        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        btnRock.setOnClickListener { play(Move.ROCK) }
        btnPaper.setOnClickListener { play(Move.PAPER) }
        btnScissors.setOnClickListener { play(Move.SCISSORS) }
        getStatistics()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.history -> {
                openHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun play(userMove: Move) {
        val pcMove = Move.getRandomMove()
        val result = decideWinner(userMove, pcMove)
        val game = Game(date = Date(), result = result, pcMove = pcMove, userMove = userMove)
        showResult(game)
        saveGame(game)
    }

    private fun showResult(game: Game) {
        val text = "It's a " + game.result.toString()
        tvResult.text = text
        ivPcMove.setImageDrawable(game.pcMove.getDrawable(this))
        ivUserMove.setImageDrawable(game.userMove.getDrawable(this))
    }

    private fun saveGame(game: Game) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                Log.i("GAME", game.toString())
                gameRepository.insertGame(game)
                getStatistics()
            }
        }
    }

    private fun getStatistics() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                statistics = gameRepository.getStatistics()
            }
            tvStatistics.text = "Win: %d, Draw: %d, Lose: %d"
                .format(statistics?.won, statistics?.draw, statistics?.lost)
        }
    }

    override fun onResume() {
        getStatistics()
        super.onResume()
    }

    private fun decideWinner(userMove: Move, pcMove: Move): Results {
        if (pcMove == userMove) {
            return Results.DRAW
        }
        return when (userMove) {
            Move.ROCK -> if (pcMove == Move.PAPER) Results.LOSS else Results.WIN
            Move.PAPER -> if (pcMove == Move.SCISSORS) Results.LOSS else Results.WIN
            Move.SCISSORS -> if (pcMove == Move.ROCK) Results.LOSS else Results.WIN
        }
    }

    private fun openHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}