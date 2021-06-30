package data.remote

import com.sideblind.newstest.BuildConfig
import retrofit2.Call
import retrofit2.http.*

interface NaverNewsApi {
    /**
     * curl "https://openapi.naver.com/v1/search/news.xml?query=%EC%A3%BC%EC%8B%9D&display=10&start=1&sort=sim" \
    -H "X-Naver-Client-Id: {애플리케이션 등록 시 발급받은 client id 값}" \
    -H "X-Naver-Client-Secret: {애플리케이션 등록 시 발급받은 client secret 값}" -v
     */
    @Headers(
        "X-Naver-Client-Id: ${BuildConfig.CLIENT_ID}",
        "X-Naver-Client-Secret: ${BuildConfig.SECRET_ID}"
    )
    @GET("/v1/search/news.json")
    fun searchNews(
        @Query("query") query:String = "주식",
        @Query("start") start:Int = 1,
        @Query("display") display:Int = 10,
        @Query("sort") sort:String = "date"
    ): Call<NewsResponse>
}