package com.example.demo.lib

class User {
    val userName : String

    private constructor(handleName: String) {
        this.userName = handleName
    }

    private constructor(firstName: String, lastName: String):
            this("$firstName-$lastName")

    companion object {
        @JvmStatic fun handleNamUser(handleName: String) = User(handleName)
        @JvmStatic fun fullNameUser(firstName: String, lastName: String) = User(firstName, lastName)
    }
}