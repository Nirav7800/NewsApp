package com.example.news

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsitemClicked {
    private  lateinit var mAdapter:NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView.layoutManager=LinearLayoutManager(this)
       fetchdata()
      mAdapter= NewsListAdapter(this)
        recycleView.adapter=mAdapter

    }
    private fun fetchdata() {
        val url="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
               val newsJsonarray=response.getJSONArray("articles")
                val newsarray=ArrayList<News>()

                for(i in 0 until newsJsonarray.length())
                {
                    val newsJsonObject=newsJsonarray.getJSONObject(i)
                    val news=News(newsJsonObject.getString( "title")
                    ,newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")

                    )
                    newsarray.add(news)

                }
                mAdapter.UpdateNews(newsarray)


            },
            {
                Toast.makeText(this,
                    "Something Went Wrong",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)
    }

    override fun onitemClick(item: News) {

        val builder= CustomTabsIntent.Builder();
       val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));

    }
}