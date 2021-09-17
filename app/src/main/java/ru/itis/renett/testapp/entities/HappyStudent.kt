package ru.itis.renett.testapp.entities

import java.lang.Math.max
import kotlin.random.Random

class HappyStudent(studentId: Int, email: String, name: String?) : Student(studentId, email, name), HappyHumanInterface {

    override fun sleepWholeDay() {
        this.happinessRate = (happinessRate + Random.nextInt(-50, 100)) % 100
    }

    override fun watchAnime() {
        val value = (happinessRate + Random.nextInt(30, 55))
        this.happinessRate = max(value % 100, happinessRate)
    }
}
