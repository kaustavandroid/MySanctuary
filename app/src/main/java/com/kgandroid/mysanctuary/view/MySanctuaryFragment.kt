package com.kgandroid.mysanctuary.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.kgandroid.mysanctuary.data.AppDatabase
import com.kgandroid.mysanctuary.R
import com.kgandroid.mysanctuary.adapters.AnimalListAdapter
import com.kgandroid.mysanctuary.data.Animal
import com.kgandroid.mysanctuary.data.AnimalRepository
import com.kgandroid.mysanctuary.databinding.FragmentAllAnimalBinding
import com.kgandroid.mysanctuary.utilities.ANIMAL_LIST_PAGE_INDEX
import com.kgandroid.mysanctuary.viewmodel.AnimalViewModel
import com.kgandroid.mysanctuary.viewmodel.AnimalViewModelFactory

class MySanctuaryFragment : Fragment(), AnimalClickListener {

    private lateinit var dataBinding: FragmentAllAnimalBinding
    private val animalListAdapter =
        AnimalListAdapter(arrayListOf())

    //private val viewModel: WordViewModel by viewModels()
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

        dataBinding.clickListenerMySanctuary = this

        val dao = AppDatabase.getInstance(requireContext()).animalDao()
        val repository = AnimalRepository(dao)
        val factory = AnimalViewModelFactory(repository)
        animalViewModel = ViewModelProvider(this, factory).get(AnimalViewModel::class.java)

        dataBinding.recyclerViewAnimal.apply {
            dataBinding.recyclerViewAnimal.adapter = animalListAdapter
        }

        observeAndLoadAllAnimals()
    }

    override fun onclickAnimal(view: View, animal: Animal) {
        Toast.makeText(context, "Animal Selected " + animal.name, Toast.LENGTH_LONG).show()
        val action =
            AnimalTabHostFragmentDirections.actionAnimalTabHostFragmentToAnimalDetailsFragment(
                animal.animalId
            )
        Navigation.findNavController(view).navigate(action)
    }

    private fun observeAndLoadAllAnimals() {
        animalViewModel.getAnimalInSanctuary(isIncluded = true).observe(viewLifecycleOwner) {
            dataBinding.hasAnimals = !it.isNullOrEmpty()
            animalListAdapter.updateAnimalList(it, this)
        }
    }

    fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.viewpagerAnimal).currentItem =
            ANIMAL_LIST_PAGE_INDEX
    }
}