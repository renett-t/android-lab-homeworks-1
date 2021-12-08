package ru.itis.renett.testapp.models

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Track(
    val id: Int,
    val title: String,
    val artist: String,
    val duration: Int,
    @DrawableRes val coverId: Int,
    @RawRes val rawFileId: Int
)
