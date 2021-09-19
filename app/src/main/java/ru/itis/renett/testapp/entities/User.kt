package ru.itis.renett.testapp.entities

open class User(var email: String) {

    constructor(email: String, name: String?) : this (email) {
        this.name = name
    }

    var isSubscribed: Boolean = false
    var name: String? = null
        get() {
            return if (field != null)
                field
            else
                "Anonymous"
        }
        set(value) {
            value?.let {
                if (it.length > 2) {
                    field = value
                } else {
                    field = null
                }
            }
        }

    fun isPaidForSubscription(): Boolean {
        return isSubscribed
    }
}
