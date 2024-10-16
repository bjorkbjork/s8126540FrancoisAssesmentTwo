package com.example.s8126540francoisassessmenttwo.data

class RegisterNewUsers {

    fun verifyNewUsers(username:String,
                       password:String,
                       confirmPassword:String):Boolean{


        return (username.isNotEmpty() && password.isNotEmpty() && password == confirmPassword)

    }

}

