package ru.itis.renett.testapp.repository

import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.models.Track

object TrackRepository {
    private var i = 0;

    var tracks = arrayListOf(
        Track(++i, "Когда ты откроешь глаза", "Три дня дождя", R.drawable.cover1, 172),
        Track(++i, "Перезаряжай", "Три дня дождя", R.drawable.cover1, 190),
        Track(++i, "Вода", "Три дня дождя, MUKKA", R.drawable.cover1, 193),
        Track(++i, "Всё из-за тебя", "Три дня дождя", R.drawable.cover1, 170),
        Track(++i, "Отражения", "Три дня дождя, SHENA?", R.drawable.cover1, 183),
        Track(++i, "В кого ты влюблена", "Три дня дождя", R.drawable.cover1, 182),
        Track(++i, "Стекло", "Три дня дождя", R.drawable.cover1, 193),
        Track(++i, "Театр Теней", "Три дня дождя",R.drawable.cover1, 166),
        Track(++i, "Космос", "Три дня дождя, Лали",R.drawable.cover1, 211),
        Track(++i, "Где ты", "Три дня дождя", R.drawable.cover1, 142),
        Track(++i, "Если я умру", "Три дня дождя",R.drawable.cover1, 191),
        Track(++i, "I Was Made For Lovin' You", "KISS",R.drawable.cover2, 271),
        Track(++i, "2,000 Man", "KISS",R.drawable.cover2, 295),
        Track(++i, "Sure Know Something", "KISS", R.drawable.cover2, 241),
        Track(++i, "Dirty Livin'", "KISS",  R.drawable.cover2, 267),
        Track(++i, "Charisma", "KISS",  R.drawable.cover2, 265),
        Track(++i, "Magic Touch", "KISS",  R.drawable.cover2, 282), // and many more songs in this album i'm just being lazy
        Track(++i, "Good Day", "Twenty One Pilots", R.drawable.cover3, 204),
        Track(++i, "Choker", "Twenty One Pilots", R.drawable.cover3, 223),
        Track(++i, "Shy Away", "Twenty One Pilots", R.drawable.cover3, 175),
        Track(++i, "The Outside", "Twenty One Pilots", R.drawable.cover3, 216),
        Track(++i, "CHEESE", "Stray Kids",  R.drawable.cover4, 182),
        Track(++i, "Thunderous", "Stray Kids",  R.drawable.cover4, 183),
        Track(++i, "DOMINO", "Stray Kids",  R.drawable.cover4, 198),
        Track(++i, "SSICK", "Stray Kids",  R.drawable.cover4, 190),
        Track(++i, "The View", "Stray Kids",  R.drawable.cover4, 201),
        Track(++i, "Secret, Secret", "Stray Kids",  R.drawable.cover4, 209),
        Track(++i, "Red Lights", "Stray Kids",  R.drawable.cover4, 189),
        Track(++i, "Smells Like Teen Spirit", "Nirvana",  R.drawable.cover5, 301),
        Track(++i, "In Bloom", "Nirvana",  R.drawable.cover5, 255),
        Track(++i, "Come As You Are", "Nirvana",  R.drawable.cover5, 218),
        Track(++i, "Breed", "Nirvana",  R.drawable.cover5, 184),
        Track(++i, "Lithium", "Nirvana",  R.drawable.cover5, 257),
        Track(++i, "Polly", "Nirvana",  R.drawable.cover5, 173),
        Track(++i, "I Want You To Love Me", "Fiona Apple",  R.drawable.cover6, 237),
        Track(++i, "Shameika", "Fiona Apple",  R.drawable.cover6, 248),
        Track(++i, "Fetch The Bolt Cutters", "Fiona Apple",  R.drawable.cover6, 298),
        Track(++i, "Under The Table", "Fiona Apple",  R.drawable.cover6, 201),
        Track(++i, "Relay", "Fiona Apple",  R.drawable.cover6, 289),
        Track(++i, "Rack of His", "Fiona Apple",  R.drawable.cover6, 242),
    )

    fun getTrackById(id: Int) : Track? {
        for (j in tracks)
            if (j.id == id)
                return j
        return null
    }

    fun getSize() = tracks.size;

    fun addNewTrack(track: Track) {
        track.id = ++i
        tracks.add(track)
    }

    fun addNewTrack(index: Int, track: Track) {
        track.id = ++i
        tracks.add(index - 1, track);   // у меня индексы трэков начинаются с единички :0
    }

    fun removeTrackById(itemId: Int) {
        tracks.remove(getTrackById(itemId))
    }
}
