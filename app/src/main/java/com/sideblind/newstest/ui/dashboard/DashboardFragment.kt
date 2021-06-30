package com.sideblind.newstest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sideblind.newstest.MainActivity
import com.sideblind.newstest.R
import com.sideblind.newstest.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var tabviewAdapter: DashboardTabViewpagerAdapter
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTab()
    }

    private fun setTab() {
        tabviewAdapter = DashboardTabViewpagerAdapter(this)
        binding.pager.adapter = tabviewAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> "유사 차트"
                1 -> "관심도 분석"
                2 -> "이슈 분석"
                else -> "유사 차트"
            }
        }.attach()
    }
}