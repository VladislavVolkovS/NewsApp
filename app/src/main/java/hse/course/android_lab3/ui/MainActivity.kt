package hse.course.android_lab3.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hse.course.android_lab3.adapter.NewsAdapter
import hse.course.android_lab3.R
import hse.course.android_lab3.api.NewsDataApi
import hse.course.android_lab3.model.NewsDataApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val newsDataApi: NewsDataApi = NewsDataApi.create()
    private lateinit var newsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        supportActionBar?.hide()
        setContentView(R.layout.main_activity_layout)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        newsRecyclerView = findViewById(R.id.news_recycler_view)
        newsRecyclerView.layoutManager = linearLayoutManager
        newsRecyclerView.adapter = NewsAdapter(ArrayList())

        val searchButton: ImageButton = findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            searchNews()
        }
    }

    private fun searchNews() {
        val searchEditText: EditText = findViewById(R.id.search_edit_text)
        val query: String = searchEditText.text.toString()

        if (query.isEmpty()) {
            Toast.makeText(this, "Enter a request", Toast.LENGTH_LONG).show()
            return
        }

        newsDataApi.get(
            getString(R.string.apikey),
            query,
            getString(R.string.language)
        )
            .enqueue(object : Callback<NewsDataApiResponse> {

                override fun onResponse(
                    call: Call<NewsDataApiResponse>,
                    response: Response<NewsDataApiResponse>
                ) {
                    if (response.isSuccessful) {
                        val newsDataApiResponse: NewsDataApiResponse? = response.body()
                        if (newsDataApiResponse?.results != null) {
                            newsRecyclerView.adapter = NewsAdapter(newsDataApiResponse.results)
                            Toast.makeText(
                                applicationContext,
                                "Results found: " + newsDataApiResponse.results.size,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Server error: " + response.code(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<NewsDataApiResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(applicationContext, "Error during search", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }
}
