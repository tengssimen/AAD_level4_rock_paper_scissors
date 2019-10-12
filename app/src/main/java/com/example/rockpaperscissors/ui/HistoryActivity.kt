package com.example.rockpaperscissors.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.GameRepository
import com.example.rockpaperscissors.models.entity.Game
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private lateinit var gameRepository: GameRepository
    private val gamesList = arrayListOf<Game>()
    private val gameHistoryAdapter = GameHistoryAdapter(gamesList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Your Game History"

        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        rvGameHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvGameHistory.adapter = gameHistoryAdapter
        rvGameHistory.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        getGamesList()
    }

    private fun getGamesList() {
        CoroutineScope(Dispatchers.Main).launch {
            val shoppingList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryActivity.gamesList.clear()
            this@HistoryActivity.gamesList.addAll(shoppingList)
            this@HistoryActivity.gameHistoryAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.history_menu, menu)
        return true
    }

    private fun deleteHistory() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGamesList()
        }
        Toast.makeText(this, "Deleted game history..", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                deleteHistory()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}