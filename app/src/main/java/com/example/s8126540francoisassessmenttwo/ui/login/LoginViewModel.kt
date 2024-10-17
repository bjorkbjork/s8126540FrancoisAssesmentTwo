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
import com.squareup.moshi.JsonDataException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RestfulApiDevRepositoryClass, private val exceptions: Exceptions): ViewModel() {

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

                     Log.v("NIT3213", "Exception: $ex");

                     // set keypass to null, as something has gone wrong
                     keypass.value = null

                    // set returned error based on value of ex
                     errors.value = when(ex){
                         is SocketTimeoutException -> exceptions.timeout
                         is HttpException -> {
                             when (ex.code()){
                                 404 -> exceptions.noSuchDetails
                                 400 -> exceptions.invalidDetails
                                 500 -> exceptions.serverError
                                 else -> ex
                             }
                         }
                         is JsonDataException -> exceptions.jsonError
                         is UnknownHostException -> exceptions.offline
                         else -> ex
                     }
                 }
                // return tuple, keypass and errors
                Pair(keypass,errors)
            }
    }


    val text: LiveData<String> = _text
}