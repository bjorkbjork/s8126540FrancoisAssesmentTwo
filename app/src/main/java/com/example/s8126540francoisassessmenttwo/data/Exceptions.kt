package com.example.s8126540francoisassessmenttwo.data

data class Exceptions(

    val timeout:Exception = Exception("timeout"),
    val invalid:Exception = Exception("invalid"),
    val offline:Exception = Exception("offline"),
    ){
    fun httpErrors(){

    }

}