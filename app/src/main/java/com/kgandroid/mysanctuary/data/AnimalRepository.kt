package com.kgandroid.mysanctuary.data

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class AnimalRepository(private val animalDao: AnimalDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allAnimal: LiveData<List<Animal>> = animalDao.getAllAnimal()

    fun getAnimalByHabitat(habitat: String) = animalDao.getAnimalByHabitat(habitat)

    fun getAnimalInSanctuary(isIncluded: Boolean) = animalDao.getAnimalInSanctuary(isIncluded)

    fun getAnimalById(animalId: String) = animalDao.getAnimalById(animalId)

    fun updateIsIncluded(isIncluded: Boolean,animalId: String) = animalDao.updateIsIncluded(isIncluded , animalId)





    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AnimalRepository? = null

        fun getInstance(animalDao: AnimalDao) =
            instance ?: synchronized(this) {
                instance ?: AnimalRepository(animalDao).also { instance = it }
            }
    }
}