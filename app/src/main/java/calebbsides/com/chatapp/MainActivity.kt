package calebbsides.com.chatapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import calebbsides.com.chatapp.adapters.ViewPagerAdapter
import calebbsides.com.chatapp.fragments.ChatFragment
import calebbsides.com.chatapp.fragments.ProfileFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = tabLayout_id
        viewPager = viewPager_id

        var adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(ChatFragment(), "Chat")
        adapter.addFragment(ProfileFragment(), "Profile")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
