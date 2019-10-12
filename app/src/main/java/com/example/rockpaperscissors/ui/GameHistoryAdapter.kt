package com.example.rockpaperscissors.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.models.entity.Game
import com.example.rockpaperscissors.models.enums.Results
import kotlinx.android.synthetic.main.single_game_view.view.*

internal class GameHistoryAdapter(private val games: List<Game>, private val context: Context) :
    RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_game_view, parent, false)
        )
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(games[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvResult.text = parseResult(game.result)
            itemView.tvDate.text = game.date.toString()
            itemView.ivUserMove.setImageDrawable(game.userMove.getDrawable(context))
            itemView.ivPcMove.setImageDrawable(game.userMove.getDrawable(context))
        }

        private fun parseResult(result: Results): String {
            return when (result) {
                Results.WIN -> "You win!"
                Results.LOSS -> "Computer wins!"
                Results.DRAW -> "Draw!"
            }
        }
    }
}