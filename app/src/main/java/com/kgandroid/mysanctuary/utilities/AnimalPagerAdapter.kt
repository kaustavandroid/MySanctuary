package com.kgandroid.mysanctuary.utilities

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kgandroid.mysanctuary.view.AllAnimalsFragment
import com.kgandroid.mysanctuary.view.MySanctuaryFragment

const val MY_SANCTUARY_PAGE_INDEX = 0
const val ANIMAL_LIST_PAGE_INDEX = 1

class AnimalPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_SANCTUARY_PAGE_INDEX to { MySanctuaryFragment() },
        ANIMAL_LIST_PAGE_INDEX to { AllAnimalsFragment()  }
    )

    override fun getItemCount() = tabFragmentsCreators.size

override fun createFragment(position: Int): Fragment {
    return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}
}