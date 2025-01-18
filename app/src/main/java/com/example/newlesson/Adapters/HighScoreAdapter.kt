package com.example.newlesson

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newlesson.Interfaces.MapLocationUpdater
import com.example.newlesson.Util.TimeFormatter
import com.example.newlesson.objects.HighScoreData
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView


// HighScoreAdapter.kt
class HighScoreAdapter(
    private val highScores: List<HighScoreData>,
    private val locationUpdater: MapLocationUpdater // This will be passed from the activity
) : RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.high_score_item, parent, false)
        return HighScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        val highScoreData = highScores[position]
        holder.playerTextView.text = highScoreData.playerName
        holder.scoreTextView.text = TimeFormatter.formatHighScoreTime(highScoreData.score)
        holder.locationButton.setOnClickListener {
            locationUpdater.updateMapToLocation(highScoreData.latitude, highScoreData.longitude)
            Toast.makeText(holder.itemView.context, "Lat: ${highScoreData.latitude}, Long: ${highScoreData.longitude}", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int = highScores.size

    class HighScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerTextView: MaterialTextView = itemView.findViewById(R.id.playerTextView)
        val scoreTextView: MaterialTextView = itemView.findViewById(R.id.scoreTextView)
        val locationButton: MaterialButton = itemView.findViewById(R.id.locationButton)
    }
}




