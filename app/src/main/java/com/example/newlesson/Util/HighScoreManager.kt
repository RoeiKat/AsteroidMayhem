import android.content.Context
import com.example.newlesson.objects.HighScoreData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HighScoreManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("high_scores_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private fun saveHighScores(highScores: ArrayList<HighScoreData>) {
        val json = gson.toJson(highScores)
        sharedPreferences.edit().putString("high_scores", json).apply()
    }

    private fun getHighScores(): ArrayList<HighScoreData> {
        val json = sharedPreferences.getString("high_scores", null)
        val type = object : TypeToken<ArrayList<HighScoreData>>() {}.type
        return gson.fromJson(json, type) ?: ArrayList()
    }

    fun updateHighScores(newScore: HighScoreData): ArrayList<HighScoreData> {
        val highScores = getHighScores()
        highScores.add(newScore)
        highScores.sortByDescending { it.score }
        saveHighScores(highScores)
        return highScores
    }
}


