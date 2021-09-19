package ru.itis.renett.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.itis.renett.testapp.entities.HappyStudent
import ru.itis.renett.testapp.entities.Student
import ru.itis.renett.testapp.entities.Teacher

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mari = Student(12,  "mari@mail.ru", "Mari")
        var jolyne = HappyStudent(42, "JoLyNe@ma.com","Jolyne")
        var vanya = Student(13, "myemail@gmail.com", null)
        var teacherFerenets = Teacher("teachers@mail.ya", "Ferya")
        var listOfStudents = arrayListOf(mari, jolyne, vanya)

        println("\n________________________ - uni life: start!")

        mari.study(5.3)
        jolyne.study(3.2)
        vanya.study(8.2)
        for (student in listOfStudents) {
            student.getState()
        }

        println("\n________________________ - after watching anime:")

        jolyne.watchAnime()
        for (student in listOfStudents) {
            student.getState()
        }

        println("\n________________________ - teacher held a lesson and checked hw:")

        teacherFerenets.teachAClass()
        teacherFerenets.checkStudentsHomeworks(listOfStudents)
        for (student in listOfStudents) {
            student.getState()
        }
    }
}
