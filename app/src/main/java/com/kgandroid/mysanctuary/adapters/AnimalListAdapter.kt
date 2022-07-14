package com.kgandroid.mysanctuary.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kgandroid.mysanctuary.R
import com.kgandroid.mysanctuary.data.Animal
import com.kgandroid.mysanctuary.databinding.ListItemAnimalBinding
import com.kgandroid.mysanctuary.view.AnimalClickListener
import com.kgandroid.mysanctuary.viewmodel.AnimalViewModel
import kotlin.collections.ArrayList

class AnimalListAdapter(val animalList: ArrayList<Animal>) : RecyclerView.Adapter<AnimalListAdapter.ViewHolder>()  {
    private var itemListener: AnimalClickListener? = null


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ListItemAnimalBinding>(
            inflater,
            R.layout.list_item_animal,
            parent,
            false
        )
        return ViewHolder(v)
    }

    fun updateAnimalList(newStudentList: List<Animal>, itemListener: AnimalClickListener) {
        animalList.clear()
        animalList.addAll(newStudentList)
        this.itemListener = itemListener
        notifyDataSetChanged()
    }


    //this method i binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(animalList[position])
    }

    /*companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Word>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldStudent: Word,
                                         newStudent: Word) = oldStudent.wordId == newStudent.wordId

            override fun areContentsTheSame(oldStudent: Word,
                                            newStudent: Word) = oldStudent == newStudent
        }
    }*/


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return animalList.size
    }


    //the class is hodling the list view
    inner class ViewHolder( val binding: ListItemAnimalBinding) : RecyclerView.ViewHolder(binding.root),
        AnimalClickListener {
        fun bindItems(animal: Animal) {
            binding.listener = this
            binding.animal = animal
        }
        override fun onclickAnimal(v: View, animal: Animal) {
            Log.d("Word Clicked" , animal.name)
            itemListener?.onclickAnimal(v, animal)
        }

    }
}