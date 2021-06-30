package adapter

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.sideblind.newstest.App
import com.sideblind.newstest.R
import com.sideblind.newstest.databinding.ItemNewsBinding
import data.remote.NewsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsAdapter(
    var datas: ArrayList<NewsData>,
    val itemClick: (String) -> Unit,
    val setImage: (String, Int) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    inner class ViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(data: NewsData) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
                binding.titleTV.text = Html.fromHtml(data.title)
                //binding.descriptionTV.text = Html.fromHtml(data.description)
            } else {
                binding.titleTV.text = Html.fromHtml(data.title, Html.FROM_HTML_MODE_LEGACY)
                //binding.descriptionTV.text = Html.fromHtml(data.description, Html.FROM_HTML_MODE_LEGACY)
            }

            //App.util.getPressCompanyName(data.originallink)

            try {
                Glide.with(binding.root).load(data.imageUrl).format(DecodeFormat.PREFER_ARGB_8888)
                    .placeholder(R.drawable.menu_news_24)
                    .override(App.util.dpTopx(128).toInt()).into(binding.itemIV)

                Log.e("data", """
                    |${data.imageUrl}
                    |${data.title}
                """.trimMargin())

            } catch (e: Exception) {
                Glide.with(binding.root).load(R.drawable.menu_news_24)
                    .format(DecodeFormat.PREFER_ARGB_8888).override(
                        App.util.dpTopx(128).toInt()
                    ).into(binding.itemIV)
            }

            binding.root.setOnClickListener {
                itemClick(data.link?:"")
            }

            if (data.imageUrl == null) {
                CoroutineScope(Dispatchers.IO).launch {
                    setImage(data.originallink?:"", adapterPosition)
                }
            }
        }

        fun clear() {
            Glide.with(binding.root).clear(binding.itemIV)
        }
    }
}