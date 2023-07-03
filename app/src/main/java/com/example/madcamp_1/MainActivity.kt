package com.example.madcamp_1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity() {

    override var viewId: Int = R.layout.activity_main
    override var toolbarId: Int? = R.id.toolbar
    private var mViewPager: ViewPager? = null

    @SuppressLint("ShowToast")
    override fun onCreate() {
        mViewPager = findViewById(R.id.pager)
        mViewPager!!.adapter = PagerAdapter(supportFragmentManager)
        mViewPager!!.currentItem = 0

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)

        tabLayout.getTabAt(0)?.text = "People"
        tabLayout.getTabAt(1)?.text = "Restaurant"
        tabLayout.getTabAt(2)?.text = "Schedule"

        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager?.currentItem = tab.position
            }


            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    inner class PagerAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> OneFragment()
                1 -> TwoFragment()
                else -> ThreeFragment()
            }
        }

        override fun getCount(): Int = 3
    }
}
