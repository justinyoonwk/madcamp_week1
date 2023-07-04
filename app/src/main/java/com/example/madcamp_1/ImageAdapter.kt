package com.example.madcamp_1

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager

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
            adjustImageSize(imageResId)
            imageView.setImageResource(imageResId)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val imageResId = imageList[position]
                    onImageClickListener?.onImageClick(imageResId)
                }
            }
        }

        private fun adjustImageSize(imageResId: Int) {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(itemView.resources, imageResId, options)
            val imageWidth = options.outWidth
            val imageHeight = options.outHeight

            val layoutParams = imageView.layoutParams as GridLayoutManager.LayoutParams
            val spanCount = (itemView.context.resources.displayMetrics.widthPixels / itemView.context.resources.displayMetrics.density / 180).toInt() // 이미지 너비가 180dp라고 가정
            val spacing = (itemView.context.resources.displayMetrics.widthPixels / spanCount - itemView.context.resources.getDimensionPixelSize(R.dimen.image_width)) / (spanCount - 1)
            layoutParams.width = itemView.context.resources.getDimensionPixelSize(R.dimen.image_width)
            layoutParams.height = (layoutParams.width * imageHeight / imageWidth)
            layoutParams.rightMargin = if ((adapterPosition + 1) % spanCount == 0) 0 else spacing
            layoutParams.bottomMargin = spacing
            imageView.layoutParams = layoutParams
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    interface OnImageClickListener {
        fun onImageClick(imageResId: Int)
    }
}