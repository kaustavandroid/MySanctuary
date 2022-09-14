package com.kgandroid.mysanctuary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.kgandroid.mysanctuary.R
import com.kgandroid.mysanctuary.databinding.FragmentAnimalTabLayoutBinding
import com.kgandroid.mysanctuary.utilities.ANIMAL_LIST_PAGE_INDEX
import com.kgandroid.mysanctuary.utilities.AnimalPagerAdapter
import com.kgandroid.mysanctuary.utilities.MY_SANCTUARY_PAGE_INDEX

class AnimalTabHostFragment : Fragment() {

    private lateinit var dataBinding: FragmentAnimalTabLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_animal_tab_layout, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataBinding.viewpagerAnimal.adapter = AnimalPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(dataBinding.tabs, dataBinding.viewpagerAnimal) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        //(activity as AppCompatActivity).setSupportActionBar(toolbar)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_SANCTUARY_PAGE_INDEX -> R.drawable.garden_tab_selector
            ANIMAL_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_SANCTUARY_PAGE_INDEX -> getString(R.string.my_sanctuary_title)
            ANIMAL_LIST_PAGE_INDEX -> getString(R.string.animal_list_title)
            else -> null
        }
    }
}