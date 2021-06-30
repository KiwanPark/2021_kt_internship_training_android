package com.sideblind.newstest.ui.dashboard.subview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sideblind.newstest.databinding.FragmentDashboardSimilarChartBinding

class DashboardSimilarChartFragment : Fragment() {

    private lateinit var binding:FragmentDashboardSimilarChartBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardSimilarChartBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Toast.makeText(context, arguments?.getInt("ARG_OBJECT").toString(), Toast.LENGTH_SHORT).show()
    }
}