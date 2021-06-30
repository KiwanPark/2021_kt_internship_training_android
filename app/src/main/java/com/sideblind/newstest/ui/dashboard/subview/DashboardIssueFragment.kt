package com.sideblind.newstest.ui.dashboard.subview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sideblind.newstest.databinding.FragmentDashboardIssueBinding
import net.alhazmy13.wordcloud.ColorTemplate
import net.alhazmy13.wordcloud.WordCloud
import java.util.*
import kotlin.collections.ArrayList


class DashboardIssueFragment : Fragment() {

    private lateinit var binding: FragmentDashboardIssueBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardIssueBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCloud()
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    private fun setCloud() {
        val list: ArrayList<WordCloud> = ArrayList()

        list.add(WordCloud("페이", Random().nextInt(3) + 1))
        list.add(WordCloud("전자", Random().nextInt(3)))
        list.add(WordCloud("노트북", Random().nextInt(3)))
        list.add(WordCloud("카드", Random().nextInt(3)))
        list.add(WordCloud("갤럭시", Random().nextInt(3)))
        list.add(WordCloud("라이온즈", Random().nextInt(3)))
        list.add(WordCloud("중고", Random().nextInt(3)))
        list.add(WordCloud("르노", Random().nextInt(3)))
        list.add(WordCloud("화재", Random().nextInt(3)))
        list.add(WordCloud("출판사", Random().nextInt(3)))
        list.add(WordCloud("야구장", Random().nextInt(3)))
        list.add(WordCloud("서울병원", Random().nextInt(3)))
        list.add(WordCloud("디자인", Random().nextInt(3)))
        list.add(WordCloud("견적", Random().nextInt(3)))
        list.add(WordCloud("플러스", Random().nextInt(3)))
        list.add(WordCloud("sk", Random().nextInt(3)))
        list.add(WordCloud("Galaxy", Random().nextInt(3)))
        list.add(WordCloud("다이렉트", Random().nextInt(3)))
        list.add(WordCloud("GSAT", Random().nextInt(3)))
        list.add(WordCloud("하반기", Random().nextInt(3)))
        list.add(WordCloud("SUHD", Random().nextInt(3)))
        list.add(WordCloud("스마트", Random().nextInt(3)))
        list.add(WordCloud("대학생", Random().nextInt(3)))
        list.add(WordCloud("서울", Random().nextInt(3)))
        list.add(WordCloud("수리", Random().nextInt(3)))
        list.add(WordCloud("액정", Random().nextInt(3)))
        list.add(WordCloud("수리", Random().nextInt(3)))
        list.add(WordCloud("ㅇ에컨", Random().nextInt(3)))
        list.add(WordCloud("미러", Random().nextInt(3)))
        list.add(WordCloud("대구", Random().nextInt(3)))
        list.add(WordCloud("결제", Random().nextInt(3)))
        list.add(WordCloud("코엑스", Random().nextInt(3)))
        list.add(WordCloud("Samsung", Random().nextInt(3)))
        list.add(WordCloud("인테리어", Random().nextInt(3)))
        list.add(WordCloud("물산", Random().nextInt(3)))
        list.add(WordCloud("교체", Random().nextInt(3)))
        list.add(WordCloud("보험", Random().nextInt(3)))
        list.add(WordCloud("나눔", Random().nextInt(3)))
        list.add(WordCloud("NX1", Random().nextInt(3)))
        list.add(WordCloud("Lite", Random().nextInt(3)))

        binding.wordCloud.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }
        binding.wordCloud.setDataSet(list)
        binding.wordCloud.setScale(20, 10);
        binding.wordCloud.setColors(ColorTemplate.MATERIAL_COLORS)
        binding.wordCloud.notifyDataSetChanged()
    }
}