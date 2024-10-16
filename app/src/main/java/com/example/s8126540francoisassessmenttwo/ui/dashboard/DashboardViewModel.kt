package com.example.s8126540francoisassessmenttwo.ui.dashboard

import android.content.ClipData.Item
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.RestfulApiDevRepositoryClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: RestfulApiDevRepositoryClass): ViewModel() {

    //var responseData = MutableLiveData<ItemData>()
    init{

    }

    private val errors = MutableStateFlow<Exception?>(value = null)
    private val responseData = MutableStateFlow<ItemData?>(value = null)

    suspend fun getData(keypass: Keypass): Pair<MutableStateFlow<ItemData?>, MutableStateFlow<Exception?>>{
//        val responseData = MutableLiveData<ItemData?>()
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//
//                val result:(suspend () -> ItemData) = suspend { repository.getAllObjectsData(keypass.keypass.toString()) }
//                responseData.postValue(result.invoke())
//
//            } catch (ex: Exception){
//                Log.v("Errors","$ex")
//                responseData.postValue(null)
//            }
//        }
//        return responseData

        // migrating to withContext to maintain synchronicity with loginViewModel

        return withContext(Dispatchers.IO){
            try{
                // since API returns ItemData(entities = [Entity(x), Entity(y)], entityTotal = z), and that is how I structured my class, create lambda function
                val result: (suspend () -> ItemData) = suspend { repository.getAllObjectsData(keypass.keypass.toString()) }
                // invoke the lambda function: expected return of result: ItemData(entities = [Entity(x), Entity(y)], entityTotal = z)
                responseData.value = (result.invoke())
                // set errors to null, as try worked
                errors.value = null

            } catch(ex:Exception){
                // set responseData to null, as something has gone wrong
                responseData.value = null
                // check for matches in Retrofit exception
                val error = Regex(ex.toString())
                // set value of errors based upon regex
                errors.value =  if (error.containsMatchIn("timeout")) Exception("timeout");
                                else if (error.containsMatchIn("400")) Exception("invalid")
                                else ex
            }
            // return tuple, keypass and errors
            Pair(responseData, errors)
        }
    }


}