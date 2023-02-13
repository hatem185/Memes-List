package com.example.memes.ui.memeslist

import android.os.Build.VERSION.SDK_INT
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.request.ImageRequest
import com.example.memes.databinding.MemeItemBinding
import com.example.memes.model.Meme
import com.example.memes.model.MemeApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class AdapterMemes @Inject constructor(
    private val imageLoader: ImageLoader,
    private val imageRequestBuilder: ImageRequest.Builder
) : ListAdapter<Meme, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Meme>() {

            override fun areItemsTheSame(oldItem: Meme, newItem: Meme): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Meme, newItem: Meme): Boolean {
                return oldItem.ups == newItem.ups
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: MemeItemBinding =
            MemeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val item = getItem(position)
            holder.bind(item)
        }
    }

    inner class ViewHolder(private val itemBinding: MemeItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Meme) {
            itemBinding.apply {
                item.apply {
                    imageLoader.enqueue(imageRequestBuilder.data(url).target(tvMemeImage).build())
                    tvMemeSubredit.text = subreddit
                    tvMemeTitle.text = title
                }
            }
        }
    }
}
