package ru.itis.renett.testapp.models

data class Album (
    val id: Int,
    val title: String,
    val artist: String,
    val coverUrl: Int,
    val date: String,           // sorry но пока это будет string а не какой-нибудь date
    val tracks: List<Track>
)
