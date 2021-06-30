package com.sideblind.newstest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BasePresenter {
    companion object{
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
    }
}