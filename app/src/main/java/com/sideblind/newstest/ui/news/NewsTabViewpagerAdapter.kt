package com.sideblind.newstest.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sideblind.newstest.ui.dashboard.subview.DashboardInterestFragment
import com.sideblind.newstest.ui.dashboard.subview.DashboardIssueFragment
import com.sideblind.newstest.ui.dashboard.subview.DashboardSimilarChartFragment
import com.sideblind.newstest.ui.news.subview.NewsInterestFragment
import com.sideblind.newstest.ui.news.subview.NewsInventoryFragment
import com.sideblind.newstest.ui.news.subview.NewsLatelyFragment

class NewsTabViewpagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = when(position) {
            0 -> NewsLatelyFragment()
            1 -> NewsInterestFragment()
            2 -> NewsInventoryFragment()
            else -> NewsLatelyFragment()
        }
        //val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply {
            putInt("ARG_OBJECT", position + 1)
        }
        return fragment
    }
}