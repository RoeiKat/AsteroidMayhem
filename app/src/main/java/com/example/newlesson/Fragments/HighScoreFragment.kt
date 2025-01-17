package com.example.newlesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newlesson.objects.HighScoreData

class HighScoreFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HighScoreAdapter

    companion object {
        fun newInstance(highScores: ArrayList<HighScoreData>): HighScoreFragment {
            val fragment = HighScoreFragment()
            val args = Bundle()
            args.putSerializable("high_scores", highScores)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_high_score, container, false)
        recyclerView = view.findViewById(R.id.highScoresRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val highScores = arguments?.getSerializable("high_scores") as ArrayList<HighScoreData>
        adapter = HighScoreAdapter(highScores)
        recyclerView.adapter = adapter
        return view
    }
}

