package data.remote

import androidx.annotation.Keep

@Keep
data class NewsResponse(
    var errorMessage:String? = null,
    var errorCode:String? = null,
    var lastBuildDate: String = "",
    var total: Long = 0,
    var start: Int = 0,
    var display: Int = 0,
    var items: ArrayList<NewsData?>? = ArrayList()
) {
}