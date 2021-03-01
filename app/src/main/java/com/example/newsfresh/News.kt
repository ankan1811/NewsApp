package com.example.newsfresh
//We have to pass an array of news type to our adapter.
data class News(
    val title: String,
    val author: String,
    val url: String,
    val imageUrl: String
)
