package com.sideblind.newstest

import adapter.NewsAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.sideblind.newstest.databinding.ActivityNewsBinding
import data.remote.NaverNewsApi
import data.remote.NewsData
import data.remote.NewsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.GetImageFromUrl
import java.util.concurrent.TimeUnit


class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    private var isSearching: Boolean = false
    private var searchStartPosition: Int = 1
    private var totalCount = Long.MAX_VALUE
    private var newsDatas: ArrayList<NewsData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRv()
    }

    fun onClick(view: View) {
        when (view) {
            binding.searchIV -> {
                search(binding.searchET.text.toString(), searchStartPosition)
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchET.windowToken, 0)
            }
        }
    }

    private fun search(query: String, startPosition: Int) {
        if (query.isNullOrBlank()) {
            Toast.makeText(this, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        isSearching = true
        searchStartPosition = if (startPosition < 0) 0 else startPosition

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.NEWS_URL) // 도메인 주소
                .addConverterFactory(GsonConverterFactory.create(gson)) // GSON을 사용하기 위해 ConverterFactory에 GSON 지정
                .client(okHttpClient)
                .build()

        retrofit.create(NaverNewsApi::class.java).searchNews(query, searchStartPosition)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    val newDatas: ArrayList<NewsData> = ArrayList()
                    try {
                        totalCount = (response.body() as NewsResponse).total
                        for (d in (response.body() as NewsResponse).items!!) {
                            if (d != null) {
                                var duplicated = false
                                for (ed: NewsData in newsDatas) {
                                    if (ed.title == d.title) {
                                        duplicated = true
                                        break
                                    }
                                }
                                if (!duplicated) {
                                    newDatas.add(d)
                                }
                            }
                        }
                    } catch (e: Exception) {

                    }

                    //Log.e("newData", newDatas.toString())
                    newsDatas.addAll(newDatas)
                    binding.searchRv.adapter?.notifyDataSetChanged()
                    isSearching = false
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.e("error", t.message.toString())
                }
            })
    }

    private fun setRv() {
        binding.searchRv.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = NewsAdapter(newsDatas,
                { link -> itemClick(link) },
                { link, position -> setImage(link, position) }
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
                    val lastVisible =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                    Log.e("onScorll", "$isSearching | ${lastVisible >= totalItemCount - 1}")
                    if (!isSearching && lastVisible >= totalItemCount - 1) {
                        search(binding.searchET.text.toString(), newsDatas.size)
                    }
                }
            })
        }
    }

    private fun itemClick(link: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("WEBURL", link)
        startActivity(intent)
    }

    private fun setImage(link: String, position: Int) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                newsDatas[position].imageUrl =
                    GetImageFromUrl(link).getImage() ?: R.drawable.menu_news_24
            } catch (e: Exception) {
                newsDatas[position].imageUrl = R.drawable.menu_news_24
            }
        }
        runBlocking {
            job.join()
        }

        runOnUiThread {
            try {
                if (newsDatas[position].imageUrl != null) {
                    binding.searchRv.adapter?.notifyItemChanged(position)
                }
            } catch (e: Exception) {

            }
        }
    }
}