package com.example.s8126540francoisassessmenttwo.ui.login

import android.content.ClipData.Item
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8126540francoisassessmenttwo.R
import com.example.s8126540francoisassessmenttwo.data.Entity
import com.example.s8126540francoisassessmenttwo.data.Exceptions
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.RestfulApiDevRepositoryClass
import com.example.s8126540francoisassessmenttwo.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RestfulApiDevRepositoryClass): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }

    val keypass = MutableStateFlow<Keypass?>(value = null)
    val errors = MutableStateFlow<Exception?>(value = null)
    val result = MutableStateFlow<Pair<MutableStateFlow<Keypass?>, MutableStateFlow<Exception?>>>(Pair(keypass, errors))

//    var responseData = MutableLiveData<ItemData>()

    init{
//        val data:Entity
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//
//                val result:(suspend () -> ItemData) = suspend { repository.getAllObjectsData("courses") }
//                responseData.postValue(result.invoke())
//
//
//            } catch (ex:Exception){
//                Log.v("Errors","$ex")
//            }
//        }
//        viewModelScope.launch {
//            val x = getData("courses")
//            delay(10000)
//            Log.v("NIT3213","$x")
//        }
    }

    suspend fun logInUser(user: User): Pair<MutableStateFlow<Keypass?>, MutableStateFlow<Exception?>> {
//            viewModelScope.launch(Dispatchers.IO) {
//                try {
//                    // since API returns Keypass(keypass="x"), and that is how I structured my class, create lambda function
//                    val result: (suspend () -> Keypass) = suspend { repository.addUser(user) }
//                    keypass.value = (result.invoke())
//                } catch (ex:Exception){
//                    Log.v("Errors","$ex")
//                    errors.value = ex
//                    keypass.value = null
//                }
//            }
//            // return keypass, plus any errors
//            result.value = Pair(keypass, errors)
//            return result.value
            return withContext(Dispatchers.IO){
                 try{
                     // since API returns Keypass(keypass="x"), and that is how I structured my class, create lambda function
                     val result: (suspend () -> Keypass) = suspend { repository.addUser(user) }
                     // invoke the lambda function: expected return of result: Keypass(keypass = "x")
                     keypass.value = (result.invoke())
                     // set errors to null, as try worked
                     errors.value = null

                 } catch(ex:Exception){

                     if (ex is SocketTimeoutException) Log.v("NIT3213", "SocketTimeout: $ex");

                     // set keypass to null, as something has gone wrong
                     keypass.value = null
                     // check for matches in Retrofit exception
                     val error = Regex(ex.toString())
                     // set value of errors based upon regex
                     errors.value = if (error.containsMatchIn("timeout")) Exception("timeout");
                                    else if (error.containsMatchIn("400")) Exception("invalid")
                                    else ex
                 }
                // return tuple, keypass and errors
                Pair(keypass,errors)
            }
    }


    val text: LiveData<String> = _text
}