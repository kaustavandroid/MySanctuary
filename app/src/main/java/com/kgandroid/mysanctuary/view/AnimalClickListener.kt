package com.kgandroid.mysanctuary.view

import android.view.View
import com.kgandroid.mysanctuary.data.Animal

interface AnimalClickListener {

    fun onclickAnimal (v : View , word :Animal)
}