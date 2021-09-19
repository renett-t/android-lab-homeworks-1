package ru.itis.renett.testapp.entities

import kotlin.math.ceil
import kotlin.random.Random

open class Student(var studentId: Int, email: String, name: String?,) : User(email, name) {
    var knowledgePoints: Int = 0  // may take value [0, +inf)
    var happinessRate: Int = 50   // may take value [-100; 100]. It's better to declare some static variables for range values...?

    fun study(hours: Double): Unit {
        println("Student ${this.name} have been studying for $hours hours straight.")
        happinessRate -= 10

        if (happinessRate < -100) {
            happinessRate = -100
        }

        knowledgePoints += 10 + ceil(Random.nextInt(1, 8) * hours).toInt()
    }

    fun getState() {
        print("Student ${this.name}; ")
        println("Happiness Rate: $happinessRate, Knowledge Points: $knowledgePoints")
    }
}
