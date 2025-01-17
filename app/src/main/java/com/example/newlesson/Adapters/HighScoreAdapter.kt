package com.example.newlesson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HighScoreAdapter(private val highScores: ArrayList<String>) :
    RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return HighScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        holder.highScoreText.text = highScores[position]
    }

    override fun getItemCount(): Int = highScores.size

    class HighScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val highScoreText: TextView = itemView.findViewById(android.R.id.text1)
    }
}
