package ru.itis.renett.testapp.models

data class Track (
    var id: Int,
    var title: String,
    var artist: String,
    val coverUrl: Int,
    var duration: Int
)
