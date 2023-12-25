package hse.course.android_lab3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hse.course.android_lab3.R
import hse.course.android_lab3.model.News

class NewsAdapter(private val newsData: ArrayList<News>) :

    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val content: TextView

        init {
            title = view.findViewById(R.id.title)
            content = view.findViewById(R.id.content)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.row_item, viewGroup, false)
        )
    }

    override fun onBindViewHolder(newsViewHolder: NewsViewHolder, position: Int) {
        newsViewHolder.title.text = newsData[position].title
        newsViewHolder.content.text = newsData[position].content
    }

    override fun getItemCount() = newsData.size

}