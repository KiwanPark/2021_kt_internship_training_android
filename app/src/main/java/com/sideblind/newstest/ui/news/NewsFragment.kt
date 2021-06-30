package com.sideblind.newstest.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sideblind.newstest.MainActivity
import com.sideblind.newstest.R
import com.sideblind.newstest.databinding.FragmentNewsBinding
import com.sideblind.newstest.ui.dashboard.DashboardTabViewpagerAdapter

class NewsFragment : Fragment() {
    private lateinit var tabviewAdapter: NewsTabViewpagerAdapter
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTab()
    }

    private fun setTab() {
        tabviewAdapter = NewsTabViewpagerAdapter(this)
        binding.pager.adapter = tabviewAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> "최신뉴스"
                1 -> "관심종목"
                2 -> "보관함"
                else -> "최신뉴스"
            }
        }.attach()
    }
}