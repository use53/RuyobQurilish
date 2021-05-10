package uz.ruyobqurilish.myruyobqurilish.splashui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerListAdapter (fragmentActivity: FragmentActivity,
                             private val fragment:MutableList<Fragment>)
    :FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int=fragment.size

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }

}