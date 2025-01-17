package com.example.newlesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HighScoreFragment : Fragment() {

    private lateinit var highScoresRecyclerView: RecyclerView
    private var highScores: ArrayList<String>? = null

    companion object {
        private const val ARG_HIGH_SCORES = "high_scores"

        fun newInstance(highScores: ArrayList<String>): HighScoreFragment {
            val fragment = HighScoreFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_HIGH_SCORES, highScores)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_high_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        highScoresRecyclerView = view.findViewById(R.id.highScoresRecyclerView)
        highScoresRecyclerView.layoutManager = LinearLayoutManager(context)

        highScores = arguments?.getStringArrayList(ARG_HIGH_SCORES)
        highScoresRecyclerView.adapter = HighScoreAdapter(highScores ?: ArrayList())
    }
}
