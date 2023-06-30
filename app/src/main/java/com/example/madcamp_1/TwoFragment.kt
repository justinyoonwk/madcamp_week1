package com.example.madcamp_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog


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
                when (imageResId) {
                    R.drawable.image1 -> {
                        // Show popup dialog specific to image1
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setTitle("Image1 Popup")
                            .setMessage("You clicked on image1")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                        dialogBuilder.create().show()
                    }

                    R.drawable.image2 -> {
                        // Show popup dialog specific to image2
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setTitle("Image2 Popup")
                            .setMessage("You clicked on image2")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                        dialogBuilder.create().show()
                    }

                    R.drawable.image3 -> {
                        // Show popup dialog specific to image3
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setTitle("Image3 Popup")
                            .setMessage("You clicked on image3")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                        dialogBuilder.create().show()
                    }
                    // Add cases for image4 to image8 here
                    else -> {
                        // Handle other images or show a default popup
                        // Show popup dialog specific to image2
                        val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setTitle("default Popup")
                            .setMessage("You clicked on nothing")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                        dialogBuilder.create().show()
                    }
                }
            }
        })
        return view
    }
}