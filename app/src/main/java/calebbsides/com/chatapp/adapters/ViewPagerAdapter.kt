package calebbsides.com.chatapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class ViewPagerAdapter(val fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList : MutableList<Fragment> = mutableListOf()
    private val fragmentListTitles : MutableList<String> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentListTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentListTitles.get(position)
    }

    fun addFragment(fragment : Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentListTitles.add(title)
    }
}