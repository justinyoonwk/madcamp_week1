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
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import android.widget.LinearLayout
import android.graphics.BitmapFactory

class TwoFragment : Fragment(), OnMapReadyCallback {

    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var googleMap: GoogleMap
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

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
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15,
            R.drawable.image16,
            R.drawable.image17,
            R.drawable.image18,
            R.drawable.image19,
            R.drawable.image20,
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

                val dialogView = layoutInflater.inflate(R.layout.popup_image, null) as LinearLayout
                val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
                val mapView = dialogView.findViewById<MapView>(R.id.mapView)

                // Calculate the aspect ratio of the image
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeResource(resources, imageResId, options)
                val imageWidth = options.outWidth
                val imageHeight = options.outHeight
                val aspectRatio = imageWidth.toFloat() / imageHeight

                // Calculate the desired height based on the width of the dialog
                val dialogWidth = resources.displayMetrics.widthPixels
                val desiredHeight = (dialogWidth / 2) / aspectRatio

                // Set the layout parameters of the ImageView and MapView
                val imageLayoutParams = LinearLayout.LayoutParams(dialogWidth / 2, desiredHeight.toInt())
                imageView.layoutParams = imageLayoutParams
                mapView.layoutParams = imageLayoutParams


                // Initialize the MapView
                mapView.onCreate(savedInstanceState)
                mapView.getMapAsync(this@TwoFragment) // Set the OnMapReadyCallback

                when (imageResId) {
                    R.drawable.image1 -> {
                        imageView.setImageResource(R.drawable.popup_image1)
                        latitude = 37.7749  // Location 1 latitude
                        longitude = -122.4194  // Location 1 longitude
                    }

                    R.drawable.image2 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 2 latitude
                        longitude = -118.2437  // Location 2 longitude
                    }

                    R.drawable.image3 -> {
                        imageView.setImageResource(R.drawable.popup_image3)
                        latitude = 34.0522  // Location 3 latitude
                        longitude = -118.2437  // Location 3 longitude
                    }

                    R.drawable.image4 -> {
                        imageView.setImageResource(R.drawable.popup_image4)
                        latitude = 34.0522  // Location 4 latitude
                        longitude = -118.2437  // Location 4 longitude
                    }

                    R.drawable.image5 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 5 latitude
                        longitude = -118.2437  // Location 5 longitude
                    }

                    R.drawable.image6 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 6 latitude
                        longitude = -118.2437  // Location 6 longitude
                    }

                    R.drawable.image7 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 7 latitude
                        longitude = -118.2437  // Location 7 longitude
                    }

                    R.drawable.image8 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image9 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image10 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image11 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image12 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image13 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image14 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image15 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image16 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image17 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image18 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image19 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    R.drawable.image20 -> {
                        imageView.setImageResource(R.drawable.popup_image2)
                        latitude = 34.0522  // Location 8 latitude
                        longitude = -118.2437  // Location 8 longitude
                    }

                    // Handle other images or show a default popup
                    else -> {
                        imageView.setImageResource(R.drawable.popup_default)
                        latitude = 0.0  // Default location latitude
                        longitude = 0.0  // Default location longitude
                    }
                }

                dialogBuilder.setView(dialogView as View)
                dialogBuilder.create().show()
            }
        })

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val markerOptions = MarkerOptions()
            .position(LatLng(latitude, longitude))
            .title("Marker Title")
        googleMap.addMarker(markerOptions)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 12f))
    }
}
