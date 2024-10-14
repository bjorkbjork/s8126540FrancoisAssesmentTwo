package com.example.s8126540francoisassessmenttwo.ui.login

import android.content.ClipData.Item
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8126540francoisassessmenttwo.data.Entity
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

import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RestfulApiDevRepositoryClass): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }

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

    suspend fun logInUser(user: User): MutableLiveData<Keypass?> {
            val keypass = MutableLiveData<Keypass?>()
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    // since API returns Keypass(keypass="x"), and that is how I structured my class, create lambda function
                    val result: (suspend () -> Keypass) = suspend { repository.addUser(user) }
                    keypass.postValue(result.invoke())
                } catch (ex:Exception){
                    Log.v("Errors","$ex")
                    keypass.postValue(null)
                }
            }
            return keypass
    }


    val text: LiveData<String> = _text
}