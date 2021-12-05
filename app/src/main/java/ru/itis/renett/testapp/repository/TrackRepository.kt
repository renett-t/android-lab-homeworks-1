package ru.itis.renett.testapp.repository

import ru.itis.renett.testapp.R
import ru.itis.renett.testapp.models.Track

object TrackRepository {

    var cursor: Int = 0

    var tracks = arrayListOf<Track>(
        Track(cursor++, "always, i'll care", "Jeremy Zucker", 147, R.drawable.alwayscare, R.raw.alwayscare),
        Track(cursor++, "Dancing With Your Ghost", "Sasha Alex Sloan", 198, R.drawable.dancingghost, R.raw.dancingghost),
        Track(cursor++, "death bed (coffee for your head)", "Powfu, beabadoobee", 173, R.drawable.deathbed, R.raw.deathbed),
        Track(cursor++, "head first", "Christian French", 154, R.drawable.headfirst, R.raw.headfirst),
        Track(cursor++, "I Can't Hate You", "Kayou., yaeow", 178, R.drawable.canthate, R.raw.canthate),
        Track(cursor++, "Let's Fall in Love for the Night", "FINNEAS", 190, R.drawable.fallinlove, R.raw.fallinlove),
        Track(cursor++, "Lost", "Maroon 5", 172, R.drawable.lost, R.raw.lost),
        Track(cursor++, "Match In The Rain", "Alec Benjamin", 159, R.drawable.matchinrain, R.raw.matchinrain),
        Track(cursor++, "Me After You", "Paul Kim", 280, R.drawable.meafteryou, R.raw.meafteryou),
        Track(cursor++, "Your Man", "Joji", 163, R.drawable.yourman, R.raw.yourman),
        Track(cursor++, "Ливень", "LILDRUGHILL", 189, R.drawable.heavyrain, R.raw.heavyrain),
        Track(cursor++, "Сирени", "Мэйклав", 178, R.drawable.lilac, R.raw.lilac),
        Track(cursor++, "Цветами радуги", "pyrokinesis", 240, R.drawable.rainbowcolors, R.raw.rainbowcolors),
        Track(cursor++, "Небом и звездой", "Джизус", 281, R.drawable.skyandstar, R.raw.skyandstar),
    )

    fun findTrackById(itemId: Int): Track? {
        return if (itemId in 0..tracks.size)
            tracks[itemId]
        else
            null
    }
}
