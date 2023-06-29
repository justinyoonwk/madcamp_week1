package com.example.madcamp_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : BaseActivity() {

    override var viewId: Int = R.layout.activity_main
    override var toolbarId: Int? = R.id.toolbar
    private var mViewPager: ViewPager? = null

    @SuppressLint("ShowToast")
    override fun onCreate() {

        mViewPager = findViewById(R.id.pager)
        mViewPager!!.adapter = PagerAdapter(supportFragmentManager)
        mViewPager!!.currentItem = 0

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)


        tabLayout.getTabAt(0)!!.text = "image 1"
        tabLayout.getTabAt(1)!!.text = "image 2"
        tabLayout.getTabAt(2)!!.text = "image 3"

        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) = tab.select()

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    inner class PagerAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {

        override fun getItem(position: Int): Fragment {

            return when (position) {
                0 ->
                    OneFragment()
                1 ->
                    TwoFragment()
                2 ->
                    ThreeFragment()
                else ->
                    TestFragment()
            }
        }

        override fun getCount(): Int = 4
    }
}