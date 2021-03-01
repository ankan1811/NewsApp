package com.example.newsfresh

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter //create instance of the NewsListAdapter class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this) //This is for Linear Layout manager(context) .
        //Similarly you have grid Layout manager where you mention the length and breadth of the grid and also staggered layout manager.
        fetchData()
        mAdapter = NewsListAdapter(this)//fetch the data and put it into the adapter
        recyclerView.adapter = mAdapter //We link the madapter (adapter that we created) to the recyclerview and the data is succesfully passed
    }

    private fun fetchData() { //We justy do the API call here 
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=359b406b081f4310a99322cbe409a1f9" //Top headlines for US 
        //Use your personal API key.This is mine
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            //We will take this news of news.kt out in MainActivity with the help of  JSON object.
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")//We need to take the articles JSON array out first from the response.
                val newsArray = ArrayList<News>()//Now we will pass this to a news list
                for(i in 0 until newsJsonArray.length()) { //iterate in the json array
                    //We only need 4 things from the json array : title,author,url,urlToImage.
                    val newsJsonObject = newsJsonArray.getJSONObject(i)//Get the ith index from the array and then use that ith index to get those 4 things.
                    val news = News( //Parsing opf data
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news) //We will just put the news object in the array
                }

                mAdapter.updateNews(newsArray)//Then we need to pass that array of type news in the adapter(updateNews function is present in NewsListAdfapter.kt)
            },
            Response.ErrorListener {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) { //Implement google custom tabs as mentioned in the documentation so that 
        //on clicking on news link it will open inside our app only and not in google chrome.
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}
