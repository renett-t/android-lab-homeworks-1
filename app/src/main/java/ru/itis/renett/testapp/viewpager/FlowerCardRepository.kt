package ru.itis.renett.testapp.viewpager

import ru.itis.renett.testapp.R

object FlowerCardRepository {
    var i = 0;

    var flowerCards = arrayListOf<FlowerCard>(
        FlowerCard(++i, "Gypsophila - Гипсофила", "Красиво красиво",
            arrayListOf(R.drawable.gypsophila1, R.drawable.gypsophila2, R.drawable.gypsophila3)),
        FlowerCard(++i, "Saintpaulia - Сенполия", "она же Узамбарская фиалка",
            arrayListOf(R.drawable.saintpaulia1, R.drawable.saintpaulia2, R.drawable.saintpaulia3)),
        FlowerCard(++i, "Myrtus - Мирт", "род вечнозелёных древесных растений с белыми пушистыми цветками, содержащими эфирные масла, семейства Миртовые. Миртом прежде называли также венок из цветущих побегов такого дерева или его ветвь — символ тишины, мира и наслаждения",
            arrayListOf(R.drawable.myrtus1, R.drawable.myrtus2)),
        FlowerCard(++i, "Sansevieria - Сансевие́рия", "род бесстеблевых вечнозелёных многолетних травянистых растений семейства Спаржевые.", arrayListOf(
            R.drawable.sansevieria1, R.drawable.sansevieria2))
    )

    fun getFlowerCardById(id: Int) = if (id-1 in 0..i) flowerCards[id-1] else null;

    fun getSize() = i;
}
