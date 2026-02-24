package com.example.bandcamp_parte_1

data class Track(
    val name: String,
    val duration: String,
    val imageUrl: String,
    val audioRes: Int = 0
)
