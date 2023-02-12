package com.example.memes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.memes.databinding.MemeItemBinding
import com.example.memes.model.Meme

class AdapterMemes() : ListAdapter<Meme, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
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
                    tvMemeImage.load(preview?.get(0))
                    tvMemeSubredit.text = subreddit
                    tvMemeTitle.text = title
                }
            }
        }
    }
}
