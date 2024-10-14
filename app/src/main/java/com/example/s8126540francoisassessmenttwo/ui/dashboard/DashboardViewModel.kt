package com.example.s8126540francoisassessmenttwo.ui.dashboard

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

import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: RestfulApiDevRepositoryClass): ViewModel() {

    //var responseData = MutableLiveData<ItemData>()
    init{

    }

    suspend fun getData(keypass: Keypass): MutableLiveData<ItemData?>{
        val responseData = MutableLiveData<ItemData?>()
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val result:(suspend () -> ItemData) = suspend { repository.getAllObjectsData(keypass.keypass.toString()) }
                responseData.postValue(result.invoke())

            } catch (ex: Exception){
                Log.v("Errors","$ex")
                responseData.postValue(null)
            }
        }
        return responseData
    }


}