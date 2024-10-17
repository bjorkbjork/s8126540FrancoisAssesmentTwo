package com.example.s8126540francoisassessmenttwo.data

import com.example.s8126540francoisassessmenttwo.R

data class Exceptions(

    val timeout:Exception = Exception(R.string.request_timed_out.toString()),
    val noSuchDetails:Exception = Exception(R.string.login_details_not_found.toString()),
    val invalidDetails:Exception = Exception(R.string.invalid_login_details.toString()),
    val offline:Exception = Exception(R.string.not_connected_to_network.toString()),
    val jsonError:Exception = Exception(R.string.json_error.toString()),
    val serverError:Exception = Exception(R.string.server_error.toString())
    ){

}