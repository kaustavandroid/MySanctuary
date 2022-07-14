package com.kgandroid.mysanctuary.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgandroid.mysanctuary.data.AnimalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AnimalDetailsViewModel(private val repository: AnimalRepository , private val animalId: String)  : ViewModel() {

    var updateRowIdMutableLiveData = MutableLiveData<Int?>()
    val updateRowIdLiveData : LiveData<Int?>
        get() = updateRowIdMutableLiveData


    val animalDetailsLiveData = repository.getAnimalById(animalId)

    fun updateIsIncluded(isIncluded: Boolean , animalId: String) = viewModelScope.launch(Dispatchers.IO) {
        val updateRowId = repository.updateIsIncluded(isIncluded , animalId)
        updateRowIdMutableLiveData.postValue(updateRowId)
        Log.d("UpdateId" , updateRowId.toString())
    }

}