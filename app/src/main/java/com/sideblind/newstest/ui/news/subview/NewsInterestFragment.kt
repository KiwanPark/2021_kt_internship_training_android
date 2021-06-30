package com.sideblind.newstest.ui.news.subview

import adapter.NewsAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sideblind.newstest.R
import com.sideblind.newstest.WebViewActivity
import com.sideblind.newstest.databinding.FragmentNewsInterestBinding
import com.sideblind.newstest.ui.news.NewsPresenter
import data.remote.NewsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.GetImageFromUrl

class NewsInterestFragment : Fragment() {

    private lateinit var binding:FragmentNewsInterestBinding
    private val presenter = NewsPresenter()
    private val query = "증권"
    
    private var isSearching: Boolean = false
    private var searchStartPosition: Int = 1
    private var newsData: ArrayList<NewsData> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search(searchStartPosition)
        setRv()
    }

    private fun setRv() {
        binding.searchRv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = NewsAdapter(newsData,
                { link -> itemClick(link) },
                { originallink, position -> setImage(originallink, position) }
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
                    val lastVisible =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                    Log.e("onScroll", "$isSearching | ${lastVisible >= totalItemCount - 1}")
                    if (!isSearching && lastVisible >= totalItemCount - 1) {
                        search(newsData.size)
                    }
                }
            })
        }
    }

    private fun search(startPosition: Int) {
        isSearching = true
        searchStartPosition = if (startPosition < 0) 0 else startPosition
        presenter.searchNews(query, startPosition) {isSuccess, data ->
            isSearching = false
            if(!isSuccess) {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
                Log.e("error", data.toString())
            } else {
                val newData = (data as ArrayList<*>)
                for(d in newData) {
                    if(!newsData.contains(d)) {
                        newsData.add(d as NewsData)
                    }
                }
                binding.searchRv.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun itemClick(link: String) {
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("webUrl", link)
        startActivity(intent)
    }

    private fun setImage(originallink: String, position: Int) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                newsData[position].imageUrl =
                    GetImageFromUrl(originallink).getImage() ?: R.drawable.menu_news_24
            } catch (e: Exception) {
                newsData[position].imageUrl = R.drawable.menu_news_24
            }
        }
        runBlocking {
            job.join()
        }

        activity?.runOnUiThread {
            try {
                if (newsData[position].imageUrl != null) {
                    binding.searchRv.adapter?.notifyItemChanged(position)
                }
            } catch (e: Exception) {

            }
        }
    }

//    override fun onClick(v: View?) {
//        when(v) {
//            binding.searchIV -> {
//                search(binding.searchET.text.toString(), searchStartPosition)
//                val imm: InputMethodManager =
//                    activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(binding.searchET.windowToken, 0)
//            }
//        }
//    }
}