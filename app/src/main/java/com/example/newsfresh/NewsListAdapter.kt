package com.example.newsfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {//We link this adapter to the recycler view present in the main activity
    //NewsViewHolder is the view Holder class

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder { //It is called when view holder is created and it will return a view holder
        //Number of view holders present on the screen=number of times this function will be called
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false) //We use layout inflator to convert xml format data to view format 
        //Because we need the data in view format only.We will inflate R.layout.item_news(item_news.xml to view format)
        val viewHolder = NewsViewHolder(view)//Then we pass this view
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder //We will create an instance of class NewsViewHolder present below and return it from here return newsViewHolder(view)
    }

    override fun getItemCount(): Int { //Called only first time 
        //calculates the number of items present in this list
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) { //newsViewHolder present below so we get access to titleView
        //This function binds the data into the view holder
        //News List Adapter is the adapter class.
        val currentItem = items[position] //take the item which is in the current position .items array list  created sabove
        holder.titleView.text = currentItem.title //we get access to title view present in NewsVierwHolder below and we will set the text to current item.
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    fun updateNews(updatedNews: ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //NewsViewHolder is the view Holder class
    //We will put the items from item_news.xml into the view holder here as itemView.
    val titleView: TextView = itemView.findViewById(R.id.title) //title view of text view.We make the title like this and this title will come inside itemview now
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}
