package data.remote

import androidx.annotation.Keep

@Keep
data class NewsData(
    var title: String? = null,
    var link: String? = null,
    var originallink: String? = null,
    var description: String? = null,
    var imageUrl: Any? = null,
    var pubDate: String? = null
) {
}