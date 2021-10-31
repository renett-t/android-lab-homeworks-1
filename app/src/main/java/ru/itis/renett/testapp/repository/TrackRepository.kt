package ru.itis.renett.testapp.repository

import ru.itis.renett.testapp.models.Track

object TrackRepository {
    private var i = 0;

    val tracks = arrayListOf<Track>(
        Track(++i, "Когда ты откроешь глаза", "Три дня дождя", 1, 172),
        Track(++i, "Перезаряжай", "Три дня дождя",1, 190),
        Track(++i, "Вода", "Три дня дождя, MUKKA",1, 193),
        Track(++i, "Всё из-за тебя", "Три дня дождя",1, 170),
        Track(++i, "Отражения", "Три дня дождя, SHENA?", 1, 183),
        Track(++i, "В кого ты влюблена", "Три дня дождя",1, 182), // and many more songs in this album i'm just being lazy
        Track(++i, "I Was Made For Lovin' You", "KISS",2, 271),
        Track(++i, "2,000 Man", "KISS",2, 295),
        Track(++i, "Sure Know Something", "KISS", 2, 241),
        Track(++i, "Dirty Livin'", "KISS",  2, 267),
        Track(++i, "Charisma", "KISS",  2, 265),
        Track(++i, "Magic Touch", "KISS",  2, 282),
        Track(++i, "Good Day", "Twenty One Pilots", 3, 204),
        Track(++i, "Choker", "Twenty One Pilots", 3, 223),
        Track(++i, "Shy Away", "Twenty One Pilots", 3, 175),
        Track(++i, "The Outside", "Twenty One Pilots", 3, 216),
        Track(++i, "CHEESE", "Stray Kids",  4, 182),
        Track(++i, "Thunderous", "Stray Kids",  4, 183),
        Track(++i, "DOMINO", "Stray Kids",  4, 198),
        Track(++i, "SSICK", "Stray Kids",  4, 190),
        Track(++i, "The View", "Stray Kids",  4, 201),
        Track(++i, "Secret, Secret", "Stray Kids",  4, 209),
        Track(++i, "Red Lights", "Stray Kids",  4, 189),
        Track(++i, "Smells Like Teen Spirit", "Nirvana",  5, 301),
        Track(++i, "In Bloom", "Nirvana",  5, 255),
        Track(++i, "Come As You Are", "Nirvana",  5, 218),
        Track(++i, "Breed", "Nirvana",  5, 184),
        Track(++i, "Lithium", "Nirvana",  5, 257),
        Track(++i, "Polly", "Nirvana",  5, 173),
        Track(++i, "I Want You To Love Me", "Fiona Apple",  6, 237),
        Track(++i, "Shameika", "Fiona Apple",  6, 248),
        Track(++i, "Fetch The Bolt Cutters", "Fiona Apple",  6, 298),
        Track(++i, "Under The Table", "Fiona Apple",  6, 201),
        Track(++i, "Relay", "Fiona Apple",  6, 289),
        Track(++i, "Rack of His", "Fiona Apple",  6, 242),

        )
    fun getAlbumTracks(albumId: Int): List<Track> {
        val list: ArrayList<Track> = arrayListOf();
        for (tr in tracks) {
            if (tr.albumId == albumId)
                list.add(tr)
        }
        return list;
    }
}
