package com.example.s8126540francoisassessmenttwo.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.RestfulApiDevRepositoryClass
import com.example.s8126540francoisassessmenttwo.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val apiResponseObjects = MutableStateFlow<List<ItemData>>(listOf())

    init{
//        viewModelScope.launch {
//            val user = User("Francois", "s8126540")
//            try {
//                val result: (suspend () -> Keypass) = suspend { repository.addUser(user) }
//                val keypass = result.invoke()
//            } catch(ex: Exception) {
//                Log.v("NIT3213", "$ex")
//            }
//        }
    }

    suspend fun logInUser(user: User): MutableLiveData<Keypass?> {
            val keypass = MutableLiveData<Keypass?>()
            viewModelScope.launch {
                try {
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