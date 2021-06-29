package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listner:NewsitemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
       val viewHolder=NewsViewHolder(view)
        view.setOnClickListener{
listner.onitemClick(items[viewHolder.adapterPosition])
      }

       return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
      val current_item=items[position]
        holder.titleView.text=current_item.tittle
        holder.author.text=current_item.author
        Glide.with(holder.itemView.context).load(current_item.news_url).into(holder.image)
    }

    override fun getItemCount(): Int {
       return items.size
    }

    fun UpdateNews(updatedItems:ArrayList<News>)
    {
        items.clear()
        items.addAll(updatedItems)
        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView:TextView=itemView.findViewById(R.id.tittle)
    val image:ImageView=itemView.findViewById(R.id.imgview)
    val author:TextView=itemView.findViewById(R.id.author)

}

interface NewsitemClicked{
    fun onitemClick(item:News)
}