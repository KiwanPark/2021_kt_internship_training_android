package com.sideblind.newstest

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import util.NewsUtil

class App : Application() {
    companion object {
        var DEBUG: Boolean = false

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        @SuppressLint("StaticFieldLeak")
        lateinit var util: NewsUtil
    }

    override fun onCreate() {
        super.onCreate()
        util = NewsUtil(applicationContext)
    }
}