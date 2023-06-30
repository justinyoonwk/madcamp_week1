package com.example.madcamp_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import android.widget.ImageView



class TwoFragment : Fragment() {

    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_two, container, false)

        imageRecyclerView = view.findViewById(R.id.imageRecyclerView)
        imageAdapter = ImageAdapter()

        // Set the layout manager with grid layout and 2 columns
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        imageRecyclerView.layoutManager = layoutManager

        imageRecyclerView.adapter = imageAdapter

        // Set the image data to the adapter (you can replace this with your own image data)
        val imageList = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            // Add more image resource IDs here
        )

        imageAdapter.setImageList(imageList)
        imageAdapter.setOnImageClickListener(object : ImageAdapter.OnImageClickListener {
            override fun onImageClick(imageResId: Int) {
                val dialogBuilder = AlertDialog.Builder(requireContext())
                    .setTitle("Image Popup")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }

// Inflate custom layout for the dialog
                val dialogView = layoutInflater.inflate(R.layout.popup_image, null)
                val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
                when (imageResId) {
                    R.drawable.image1 -> {
                        imageView.setImageResource(R.drawable.popup_image1)
                    }

                    R.drawable.image2 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                    }

                    R.drawable.image3 -> {
                        imageView.setImageResource(R.drawable.popup_image3)
                    }

                    R.drawable.image4 -> {
                        imageView.setImageResource(R.drawable.popup_image4)
                    }

                    R.drawable.image5 -> {
                        imageView.setImageResource(R.drawable.popup_image5)
                    }

                    R.drawable.image6 -> {
                        imageView.setImageResource(R.drawable.popup_image6)
                    }

                    R.drawable.image7 -> {
                        imageView.setImageResource(R.drawable.popup_image7)
                    }

                    R.drawable.image8 -> {
                        imageView.setImageResource(R.drawable.popup_image8)
                    }
                    else -> {
                        // Handle other images or show a default popup
                        // Show popup dialog specific to default
                        imageView.setImageResource(R.drawable.popup_default)
                    }
                }
                dialogBuilder.setView(dialogView)

                dialogBuilder.create().show()
            }
        })
        return view
    }
}


