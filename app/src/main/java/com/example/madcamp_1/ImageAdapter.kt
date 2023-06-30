package com.example.madcamp_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var imageList: List<Int> = emptyList()
    private var onImageClickListener: OnImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResId = imageList[position]
        holder.bind(imageResId)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun setImageList(images: List<Int>) {
        imageList = images
        notifyDataSetChanged()
    }

    fun setOnImageClickListener(listener: OnImageClickListener) {
        onImageClickListener = listener
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(imageResId: Int) {
            imageView.setImageResource(imageResId)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val imageResId = imageList[position]
                    onImageClickListener?.onImageClick(imageResId)
                }
            }
        }
    }

    interface OnImageClickListener {
        fun onImageClick(imageResId: Int)
    }
}
