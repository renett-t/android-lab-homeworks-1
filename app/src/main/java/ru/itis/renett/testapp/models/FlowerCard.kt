package ru.itis.renett.testapp.models

data class FlowerCard(
    var id: Int,
    var title: String,
    var descr: String,
    var images: List<Int>
)
