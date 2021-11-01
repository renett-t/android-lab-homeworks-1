package ru.itis.renett.testapp.repository

import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.models.Album

object AlbumRepository {

    val albumList = arrayListOf (
        // https://i.scdn.co/image/ab67616d00001e024202da6f60a9bc8c6c3d1559
        Album(1, "Когда ты откроешь глаза", "Три дня дождя", R.drawable.cover1, "2021", TrackRepository.getAlbumTracks(1)),
        //https://i.scdn.co/image/ab67616d00001e024384b6976cadaec272114022
        Album(2, "Dynasty", "KISS", R.drawable.cover2, "1979", TrackRepository.getAlbumTracks(2)),
        Album(3, "Scaled And Icy", "Twenty One Pilots", R.drawable.cover3, "2021", TrackRepository.getAlbumTracks(3)),
        Album(4, "NOEASY", "Stray Kids", R.drawable.cover4, "2021", TrackRepository.getAlbumTracks(4)),
        //https://i.scdn.co/image/ab67616d00001e02fbc71c99f9c1296c56dd51b6
        Album(5, "Nevermind (Remastered)", "Nirvana", R.drawable.cover5, "1991", TrackRepository.getAlbumTracks(5)),
        // https://i.scdn.co/image/ab67616d00001e02841292c1316c4bf85447bcd9
        Album(6, "Fetch The Bolt Cutters", "Fiona Apple", R.drawable.cover6, "2020", TrackRepository.getAlbumTracks(6)),
        Album(7, "DEAD LOVE 2 (Deluxe)", "May Wave$", R.drawable.cover7, "2021", TrackRepository.getAlbumTracks(7)),
        Album(8, "Californication", "Red Hot Chili Peppers", R.drawable.cover8, "1999", TrackRepository.getAlbumTracks(8)),
        Album(9, "I Love You.", "The Neighbourhood", R.drawable.cover9, "2013", TrackRepository.getAlbumTracks(9)),
        )

    fun getAlbumById(id: Int): Album? {
        for (a in albumList)
            if (a.id == id)
                return a
        return null
    }
}
