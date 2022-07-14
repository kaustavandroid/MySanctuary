package com.kgandroid.mysanctuary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kgandroid.mysanctuary.data.Animal
import com.kgandroid.mysanctuary.data.AnimalRepository

class AnimalViewModel(private val repository: AnimalRepository) : ViewModel() {
    val allAnimal: LiveData<List<Animal>>

    init {
        allAnimal = repository.allAnimal
    }


    fun getAnimalByHabitat(habitat : String): LiveData<List<Animal>> {
        return  repository.getAnimalByHabitat(habitat)
    }

    fun getAnimalInSanctuary(isIncluded : Boolean): LiveData<List<Animal>> {
        return  repository.getAnimalInSanctuary(isIncluded)
    }

}