package com.example.newlesson

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newlesson.Interfaces.MapLocationUpdater
import com.example.newlesson.objects.HighScoreData

class HighScoreFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HighScoreAdapter
    private var mapLocationUpdater: MapLocationUpdater? = null

    companion object {
        fun newInstance(highScores: ArrayList<HighScoreData>): HighScoreFragment {
            val fragment = HighScoreFragment()
            val args = Bundle()
            args.putSerializable("high_scores", highScores)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MapLocationUpdater) {
            mapLocationUpdater = context
        } else {
            throw RuntimeException("Must implement MapLocationUpdater")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_high_score, container, false)
        recyclerView = view.findViewById(R.id.highScoresRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        @Suppress("UNCHECKED_CAST")
        val highScores = arguments?.getSerializable("high_scores") as ArrayList<HighScoreData>
        adapter = HighScoreAdapter(highScores, mapLocationUpdater!!)
        recyclerView.adapter = adapter
        return view
    }
}


