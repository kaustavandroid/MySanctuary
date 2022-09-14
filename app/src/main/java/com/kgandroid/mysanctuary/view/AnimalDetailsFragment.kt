package com.kgandroid.mysanctuary.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kgandroid.mysanctuary.data.AppDatabase
import com.kgandroid.mysanctuary.R
import com.kgandroid.mysanctuary.data.AnimalRepository
import com.kgandroid.mysanctuary.databinding.AnimalDetailsFragmentBinding
import com.kgandroid.mysanctuary.viewmodel.AnimalDetailsViewModel
import com.kgandroid.mysanctuary.viewmodel.AnimalDetailsViewModelFactory

class AnimalDetailsFragment : Fragment() {
    private lateinit var dataBinding: AnimalDetailsFragmentBinding
    private val args: AnimalDetailsFragmentArgs by navArgs()
    private lateinit var animalDetailsViewModel: AnimalDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.animal_details_fragment, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Animal Id", args.animalId)
        val dao = AppDatabase.getInstance(requireContext()).animalDao()
        val repository = AnimalRepository(dao)
        val factory = AnimalDetailsViewModelFactory(repository, args.animalId)
        animalDetailsViewModel =
            ViewModelProvider(this, factory).get(AnimalDetailsViewModel::class.java)

        dataBinding.isAnimalAdded = false
        dataBinding.fabListener = this
        dataBinding.animalDetailsViewmodel = animalDetailsViewModel
        observeAndLoadAnimalDetails()
        observeMySanctuary()
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_animal_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = animalDetailsViewModel.animalDetailsLiveData.value.let { animal ->
            if (animal == null) {
                ""
            } else {
                getString(R.string.share_text_animal, animal.name)
            }
        }
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }


    private fun observeAndLoadAnimalDetails() {
        animalDetailsViewModel.animalDetailsLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("Update id-->", it.animalId)
                Log.d("Update word-->", it.name)
                Log.d("Update meaning-->", it.imageUrl)
                Log.d("Update date-->", it.description)
                dataBinding.animal = it
            }
        }
    }

    private fun observeMySanctuary() {
        animalDetailsViewModel.updateRowIdLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it >= 1) {
                    dataBinding.isAnimalAdded = true
                    Toast.makeText(context, "Animal added to your sanctuary", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}