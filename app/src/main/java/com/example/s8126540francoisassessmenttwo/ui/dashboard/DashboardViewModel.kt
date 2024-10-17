package com.example.s8126540francoisassessmenttwo.ui.dashboard

import android.content.ClipData.Item
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8126540francoisassessmenttwo.data.Exceptions
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.RestfulApiDevRepositoryClass
import com.squareup.moshi.JsonDataException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: RestfulApiDevRepositoryClass, private val exceptions:Exceptions): ViewModel() {

    private val errors = MutableStateFlow<Exception?>(value = null)
    private val responseData = MutableStateFlow<ItemData?>(value = null)

    suspend fun getData(keypass: Keypass): Pair<MutableStateFlow<ItemData?>, MutableStateFlow<Exception?>>{

        // migrating to withContext to maintain synchronicity with loginViewModel

        return withContext(Dispatchers.IO){
            try{
                // since API returns ItemData(entities = [Entity(x), Entity(y)], entityTotal = z), and that is how I structured my class, create lambda function
                val result: (suspend () -> ItemData) = suspend { repository.getAllObjectsData(keypass) }
                // invoke the lambda function: expected return of result: ItemData(entities = [Entity(x), Entity(y)], entityTotal = z)
                responseData.value = (result.invoke())
                // set errors to null, as try worked
                errors.value = null

            } catch(ex:Exception){
                // set responseData to null, as something has gone wrong
                responseData.value = null

                // check type of exception, and provide custom exception message
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
            Pair(responseData, errors)
        }
    }


}