package com.kgandroid.mysanctuary.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
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

class AllAnimalsFragment : Fragment(), AnimalClickListener {

    private lateinit var dataBinding: FragmentAllAnimalBinding
    private val animalListAdapter =
        AnimalListAdapter(arrayListOf())

    private lateinit var animalViewModel: AnimalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_animal, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = AppDatabase.getInstance(requireContext()).animalDao()
        val repository = AnimalRepository(dao)
        val factory = AnimalViewModelFactory(repository)
        animalViewModel = ViewModelProvider(this, factory)[AnimalViewModel::class.java]

        animalViewModel.allAnimal

        dataBinding.recyclerViewAnimal.apply {
            dataBinding.recyclerViewAnimal.adapter = animalListAdapter
        }

        observeAndLoadAllAnimals()
        initMenuItems()
    }

    private fun initMenuItems() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_animal_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.all_animals -> {
                        observeAndLoadAllAnimals()
                        true
                    }
                    R.id.animal_herbivorous -> {
                        observeAndLoadAnimalsByHabitat("Herbivorous")
                        true
                    }
                    R.id.animal_carnivorous -> {
                        observeAndLoadAnimalsByHabitat("Carnivorous")
                        true
                    }
                    R.id.animal_omnivorous -> {
                        observeAndLoadAnimalsByHabitat("Omnivorous")
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onclickAnimal(v: View, word: Animal) {
        Toast.makeText(context, "Word Selected " + word.name, Toast.LENGTH_LONG).show()
        val action =
            AnimalTabHostFragmentDirections.actionAnimalTabHostFragmentToAnimalDetailsFragment(
                word.animalId
            )
        Navigation.findNavController(v).navigate(action)
    }

    private fun observeAndLoadAnimalsByHabitat(habitat: String) {
        animalViewModel.getAnimalByHabitat(habitat).observe(viewLifecycleOwner) {
            if (it != null) {
                val size = it.size
                for (i in 0 until size) {
                    Log.i("ListItems", "$i --> " + it[i].name + " -->" + it[i].animalId)
                }
                animalListAdapter.updateAnimalList(it, this)
            }
        }
    }

    private fun observeAndLoadAllAnimals() {
        animalViewModel.allAnimal.observe(viewLifecycleOwner) {
            dataBinding.hasAnimals = !it.isNullOrEmpty()
            if (it != null) {
                val size = it.size
                for (i in 0 until size) {
                    Log.i("ListItems", "$i --> " + it[i].name + " -->" + it[i].animalId)
                }
                animalListAdapter.updateAnimalList(it, this)
            }
        }
    }
}


