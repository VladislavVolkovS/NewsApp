package hse.course.android_lab3.api

import hse.course.android_lab3.model.NewsDataApiResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsDataApi {

    @GET("api/1/news")
    fun get(
        @Query("apikey") apiKey: String,
        @Query("q") query: String,
        @Query("language") language: String
    ): Call<NewsDataApiResponse>

    companion object Factory {

        fun create(): NewsDataApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsdata.io/").build()

            return retrofit.create(NewsDataApi::class.java)
        }
    }
}