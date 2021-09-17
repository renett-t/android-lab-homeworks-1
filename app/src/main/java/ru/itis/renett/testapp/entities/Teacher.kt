package ru.itis.renett.testapp.entities

import kotlin.random.Random

class Teacher(email: String, name: String?): User(email, name), TeachInterface {

    override fun teachAClass() {
        println("Teacher ${this.name} held a lesson for students.")
    }

    override fun checkStudentsHomeworks(studentList: List<Student>) {
        for (student in studentList) {
            println("Teacher ${this.name} is checking Student ${student.name}'s homework.")
            student.happinessRate = (student.happinessRate + Random.nextInt(-10, 10)) % 100
        }
    }
}
