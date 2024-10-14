package com.example.s8126540francoisassessmenttwo.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8126540francoisassessmenttwo.data.Entity
import com.example.s8126540francoisassessmenttwo.data.ItemData
import com.example.s8126540francoisassessmenttwo.data.Keypass
import com.example.s8126540francoisassessmenttwo.data.RestfulApiDevRepositoryClass
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: RestfulApiDevRepositoryClass, private val itemDataAdapter: JsonAdapter<ItemData>): ViewModel() {

    //var responseData = MutableLiveData<ItemData>()
    init{

    }


    suspend fun getData(keypass: Keypass): MutableLiveData<ItemData?> {
        val responseData = MutableLiveData<ItemData?>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                responseData.postValue(parseJson(keypass))
            } catch (ex: Exception) {
                Log.v("Errors", "$ex")
                responseData.postValue(null)
            }
        }
        return responseData
    }

    suspend fun parseJson(key: Keypass): ItemData? {
        val jsonString = repository.getAllObjectsData(key.keypass.toString()).toString()
        return try {
            itemDataAdapter.fromJson(jsonString)
        } catch (e: Exception) {
            Log.v("ParseError", "$e")
            null
        }
    }

}