package com.kgandroid.mysanctuary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kgandroid.mysanctuary.data.AnimalRepository
import java.lang.IllegalArgumentException

class AnimalDetailsViewModelFactory (private val repository: AnimalRepository , private val animalId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AnimalDetailsViewModel::class.java)){
            return AnimalDetailsViewModel(repository,animalId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}