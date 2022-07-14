package com.kgandroid.mysanctuary.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kgandroid.mysanctuary.data.AppDatabase
import com.kgandroid.mysanctuary.R
import com.kgandroid.mysanctuary.adapters.AnimalListAdapter
import com.kgandroid.mysanctuary.data.Animal
import com.kgandroid.mysanctuary.data.AnimalRepository
import com.kgandroid.mysanctuary.databinding.FragmentAllAnimalBinding
import com.kgandroid.mysanctuary.viewmodel.AnimalViewModel
import com.kgandroid.mysanctuary.viewmodel.AnimalViewModelFactory

class AllAnimalsFragment : Fragment() , AnimalClickListener{

    private lateinit var dataBinding : FragmentAllAnimalBinding
    private val animalListAdapter =
        AnimalListAdapter(arrayListOf())
    //private val viewModel: WordViewModel by viewModels()
    private lateinit var animalViewModel: AnimalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_all_animal , container ,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = AppDatabase.getInstance(requireContext()).animalDao()
        val repository = AnimalRepository(dao)
        val factory = AnimalViewModelFactory(repository)
        animalViewModel = ViewModelProvider(this,factory).get(AnimalViewModel::class.java)

        animalViewModel.allAnimal

        dataBinding.recyclerViewAnimal.apply {
            dataBinding.recyclerViewAnimal.adapter = animalListAdapter
        }

        observeAndLoadAllAnimals()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_animal_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.all_animals -> {
                observeAndLoadAllAnimals()
                true
            }
            R.id.animal_herbivorous -> {
                observeAndloadAnimalsByHabitat("Herbivorous")
                true
            }
            R.id.animal_carnivorous -> {
                observeAndloadAnimalsByHabitat("Carnivorous")
                true
            }
            R.id.animal_omnivorous -> {
                observeAndloadAnimalsByHabitat("Omnivorous")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onclickAnimal(view : View, animal: Animal) {
        Toast.makeText(context, "Word Selected " + animal.name, Toast.LENGTH_LONG).show()
        val action  = AnimalTabHostFragmentDirections.actionAnimalTabHostFragmentToAnimalDetailsFragment(animal.animalId)
        Navigation.findNavController(view).navigate(action)
    }

    private fun observeAndloadAnimalsByHabitat(habitat : String){
        animalViewModel.getAnimalByHabitat(habitat).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val size = it.size
                for (i in 0..size-1) {
                    Log.i("ListItems", "$i --> " + it.get(i).name+" -->" + it.get(i).animalId)
                }
                animalListAdapter.updateAnimalList(it , this)
            }
        })
    }

    private fun observeAndLoadAllAnimals(){
        animalViewModel.allAnimal.observe(viewLifecycleOwner, Observer {
            dataBinding.hasAnimals = !it.isNullOrEmpty()
            if (it != null) {
                val size = it.size
                for (i in 0..size-1) {
                    Log.i("ListItems", "$i --> " + it.get(i).name+" -->" + it.get(i).animalId)
                }
                animalListAdapter.updateAnimalList(it , this)
            }
        })
    }
}


