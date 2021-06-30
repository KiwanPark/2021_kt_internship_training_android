package com.sideblind.newstest.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sideblind.newstest.ui.dashboard.subview.DashboardInterestFragment
import com.sideblind.newstest.ui.dashboard.subview.DashboardIssueFragment
import com.sideblind.newstest.ui.dashboard.subview.DashboardSimilarChartFragment

class DashboardTabViewpagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = when(position) {
            0 -> DashboardSimilarChartFragment()
            1 -> DashboardInterestFragment()
            2 -> DashboardIssueFragment()
            else -> DashboardSimilarChartFragment()
        }
        //val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply {
            putInt("ARG_OBJECT", position + 1)
        }
        return fragment
    }
}