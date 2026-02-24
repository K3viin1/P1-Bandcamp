package com.example.bandcamp_parte_1

data class Disc(
    val title: String,
    val artist: String,
    val imageUrl: String,
    val audioRes: Int,
    val tracks: List<Track> = emptyList()
)
