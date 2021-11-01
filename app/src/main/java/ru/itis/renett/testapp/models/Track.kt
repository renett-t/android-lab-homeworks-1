package ru.itis.renett.testapp.models

data class Track (
    val id: Int,
    val title: String,
    val artist: String,
    val albumId: Int,
    val duration: Int
)
