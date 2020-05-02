package com.harzzy.trakcov.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.harzzy.trakcov.R
import com.harzzy.trakcov.api.response.continent.ContinentResponse
import com.harzzy.trakcov.api.response.continent.ContinentResponseItem
import com.harzzy.trakcov.api.response.international.InternationalResponseItem
import com.harzzy.trakcov.api.response.news.Article
import com.harzzy.trakcov.databinding.ItemNewsFragmentBinding
import com.harzzy.trakcov.databinding.ItemStateItemBinding
import com.harzzy.trakcov.utils.Converter

/* Created by : Osemwingie Oshodin (codose)
*
* Date : 27th April, 2020
*
* */

class NewsAdapter(val context : Context, val clickListener: ArticleClickListener) : ListAdapter<Article, NewsAdapter.MyViewHolder>(ArticleDiffCallback()) {

    class MyViewHolder(val binding: ItemNewsFragmentBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(article: Article, clickListener: ArticleClickListener) {
            binding.itemCardView.setOnClickListener {
                clickListener.onClick(article)
            }
            binding.newsDateText.text = "Published at : ${Converter.formatNewsPublishedDate(article.publishedAt)}"
            binding.newsDescriptionText.text = article.description
            binding.newsTitleTextView.text = article.title
            binding.newsSourceText.text = "Source : ${article.source.name}"
            binding.newsAuthorText.text = "Author : ${article.author}"

            Glide.with(context)
                .load(article.urlToImage)
                .placeholder(R.drawable.newspaper)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.newsImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }
    private fun from(parent: ViewGroup) : MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsFragmentBinding.inflate(layoutInflater,parent,false)
        return MyViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article, clickListener)
    }
}

class ArticleClickListener(val clickListener: (article: Article) -> Unit){
    fun onClick(article: Article) = clickListener(article)
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.author == newItem.author
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
