package util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class GetImageFromUrl(private val url: String) {

    fun getImage(): String? {
        return getImage(url)
    }

    fun getImage(title:String, mUrl:String) : String? {
        //dLog.e("$title : ${getImage(mUrl)}")
        return getImage(mUrl)
    }

    private fun getImage(mUrl: String?): String? {
        var result: String? = null
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val con: org.jsoup.Connection = Jsoup.connect(mUrl)
                val doc: Document = con.get()
                var img: String = ""

                if (!doc.select("meta[property=og:image]")[0].toString().isNullOrEmpty()) {
                    result = doc.select("meta[property=og:image]")[0].attr("content")
                } else {
                    val imgs: Elements = doc.getElementsByTag("img")
                    //Log.e("document", "$doc")

                    //Log.e("Count", "${imgs.size}")
                    for (imageElement in imgs) {
                        //Log.e("imageElement null", "$imageElement")
                        if (imageElement != null) {
                            //for each element get the srs url
                            img = imageElement.absUrl("src")
                            if (!img.contains("doubleclick.net") &&
                                !img.contains("feedburner.com") &&
                                !img.contains("feedsportal.com") &&
                                !img.contains("ads") &&
                                !img.contains("logo") &&
                                !img.contains("img_blank")
                            ) {
                                result = img
                                break
                            }
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
        runBlocking {
            job.join()
        }
        //dLog.e("$mUrl : $result")
        return result
    }
}