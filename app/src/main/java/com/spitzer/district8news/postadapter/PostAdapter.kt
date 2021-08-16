package com.spitzer.district8news.postadapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spitzer.district8news.R
import com.spitzer.district8news.core.extensions.listenToClick
import com.spitzer.district8news.core.repository.data.Post
import com.spitzer.district8news.databinding.PostItemBinding
import com.squareup.picasso.Picasso

class PostAdapter(
    private var items: ArrayList<Post>,
    private val drawableFallbackImage: Drawable
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    lateinit var onItemClick: (Post) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val item =
            PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item).listenToClick { position, _ ->
            onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun onItemClickFunction(itemClickFunction: (Post) -> Unit) {
        onItemClick = itemClickFunction
    }

    fun updateData(updatedData: ArrayList<Post>) {
        items = updatedData
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val itemBinding: PostItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Post) {
            itemBinding.postTitle.text = item.title.toString()
            itemBinding.postLocation.text = "Delf -----"
            try {
                Picasso.get()
                    .load(item.featuredMedia.href)
                    .placeholder(R.drawable.ic_placeholder_image_24)
                    .error(R.drawable.ic_broken_image_24)
                    .into(itemBinding.postImage)
            } catch (e: Exception) {
                itemBinding.postImage.setImageDrawable(drawableFallbackImage)
            }
        }
    }
}