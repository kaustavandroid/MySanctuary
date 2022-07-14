/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kgandroid.mysanctuary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgandroid.mysanctuary.data.Animal

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface AnimalDao {
    @Query("SELECT * FROM sanctuary ORDER BY name")
    fun getAllAnimal(): LiveData<List<Animal>>

    @Query("SELECT * FROM sanctuary  WHERE habitat = :habitat ORDER BY name")
    fun getAnimalByHabitat(habitat : String): LiveData<List<Animal>>

    @Query("SELECT * FROM sanctuary WHERE id = :animalId")
    fun getAnimalById(animalId: String): LiveData<Animal>

    @Query("SELECT * FROM sanctuary WHERE isIncluded = :isIncluded ORDER BY name")
    fun getAnimalInSanctuary(isIncluded: Boolean): LiveData<List<Animal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animals: List<Animal>):List<Long>

    @Query("UPDATE sanctuary set isIncluded = :isIncluded WHERE id = :animalId")
    fun updateIsIncluded(isIncluded: Boolean , animalId: String) : Int
}
