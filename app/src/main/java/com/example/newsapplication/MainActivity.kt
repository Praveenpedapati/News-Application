package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNews()
    }
    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in", 1)
        news.enqueue(object : Callback<News> {

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if(news!= null){
                    Log.d("abc", news.toString())
                    val newsList = findViewById<RecyclerView>(R.id.newsList)
                    adapter = NewsAdapter(this@MainActivity,news.articles)
                    newsList.adapter = adapter
                    newsList.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("abc", "Error in fetching News", t)
            }

        })
    }
}