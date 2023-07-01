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
import androidx.constraintlayout.widget.ConstraintLayout

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
                val dialogView = layoutInflater.inflate(R.layout.popup_image, null) as LinearLayout
                val imageView = dialogView.findViewById<ImageView>(R.id.imageView)
                val mapView = dialogView.findViewById<MapView>(R.id.mapView)

                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f
                )
                imageView.layoutParams = layoutParams
                mapView.layoutParams = layoutParams


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
