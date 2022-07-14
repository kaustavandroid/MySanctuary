package com.kgandroid.mysanctuary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kgandroid.mysanctuary.data.AnimalRepository
import java.lang.IllegalArgumentException

class AnimalViewModelFactory(private val repository: AnimalRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     if(modelClass.isAssignableFrom(AnimalViewModel::class.java)){
         return AnimalViewModel(repository) as T
     }
        throw IllegalArgumentException("Unknown View Model class")
    }

}