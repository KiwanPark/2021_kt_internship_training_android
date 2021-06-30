package com.sideblind.newstest.ui.news

import android.util.Log
import com.sideblind.newstest.BasePresenter
import contract.Contract
import data.remote.NaverNewsApi
import data.remote.NewsData
import data.remote.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsPresenter : BasePresenter(), Contract.Presenter.News {
    override fun searchNews(
        query: String,
        searchStartPosition: Int,
        callback: (isSuccess: Boolean, data: Any?) -> Unit
    ) {
        retrofit.create(NaverNewsApi::class.java).searchNews(query, searchStartPosition)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    val newDatas: ArrayList<NewsData> = ArrayList()
                    try {
                        for (d in (response.body() as NewsResponse).items!!) {
                            if (d != null) {
                                newDatas.add(d)
                            }
                        }
                        callback(true, newDatas)
                    } catch (e: Exception) {
                        callback(false, e.message)
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    callback(false, t.message.toString())
                }
            })
    }

}