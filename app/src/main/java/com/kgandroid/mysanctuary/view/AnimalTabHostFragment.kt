package com.kgandroid.mysanctuary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kgandroid.mysanctuary.R

import com.kgandroid.mysanctuary.utilities.ANIMAL_LIST_PAGE_INDEX
import com.kgandroid.mysanctuary.utilities.AnimalPagerAdapter
import com.kgandroid.mysanctuary.utilities.MY_SANCTUARY_PAGE_INDEX
import kotlinx.android.synthetic.main.fragment_animal_tab_layout.*

class AnimalTabHostFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_animal_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewpagerAnimal.adapter = AnimalPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabs, viewpagerAnimal) { tab, position ->
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