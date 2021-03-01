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
    //We get the instance of the interface NewsItemClicked created below (Type of newsItemClicked)
    private val items: ArrayList<News> = ArrayList() //Now Adapter will not have any data from the beginning .It has to be fetched

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder { //It is called when view holder is created and it will return a view holder
        //Number of view holders present on the screen=number of times this function will be called
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false) //We use layout inflator to convert xml format data to view format 
        //Because we need the data in view format only.We will inflate R.layout.item_news(item_news.xml to view format)
        val viewHolder = NewsViewHolder(view)//Then we pass this view
        view.setOnClickListener{ //on click listener is also an interface
            listener.onItemClicked(items[viewHolder.adapterPosition])//items[position] we get this position from view holder
            //(whatever the position of the view holder is inside the adapter)
            //We have already passed the item in NewsItemClicked below
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
        val currentItem = items[position] //take the item which is in the current position .items array list  created above
        //we get access to title view present in NewsVierwHolder below and we will set the text to current item.
        holder.titleView.text = currentItem.title //CurrentItem is of news type
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    fun updateNews(updatedNews: ArrayList<News>) {
        items.clear()//Delete the inheritance if something still exists
        items.addAll(updatedNews)//Add the updated news to the arrayList but adapter has not been updated yet
    
        notifyDataSetChanged()//to update the adapter with the updated new news
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //NewsViewHolder is the view Holder class
    //We will put the items from item_news.xml into the view holder here as itemView.
    val titleView: TextView = itemView.findViewById(R.id.title) //title view of type textview.We make the title like this and this title will come inside itemview now
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked {
    fun onItemClicked(item: News) //We need a callback to tell the activity from adapter that an item has been clicked.(Using interfaces)
    //This is that interface.onItemClicked is present in the mainActvity.
}
