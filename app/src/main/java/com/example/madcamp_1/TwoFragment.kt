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
                imageView?.layoutParams = imageLayoutParams  // Check if imageView is null before setting layout params
                mapView?.layoutParams = imageLayoutParams  // Check if mapView is null before setting layout params



                // Initialize the MapView
                mapView.onCreate(savedInstanceState)
                mapView.getMapAsync(this@TwoFragment) // Set the OnMapReadyCallback

                val popFoodImageView = dialogView.findViewById<ImageView>(R.id.pop_food)
                val popMenuImageView = dialogView.findViewById<ImageView>(R.id.pop_menu)

                when (imageResId) {
                    R.drawable.image1 -> {
                        popFoodImageView.setImageResource(R.drawable.pop1_food)
                        popMenuImageView.setImageResource(R.drawable.pop1_menu)
                        latitude = 36.363037  // Location 1 latitude
                        longitude = 127.356207  // Location 1 longitude
                    }

                    R.drawable.image2 -> {
                        popFoodImageView.setImageResource(R.drawable.pop2_food)
                        popMenuImageView.setImageResource(R.drawable.pop2_menu)
                        latitude = 36.363657  // Location 2 latitude
                        longitude = 127.358960  // Location 2 longitude
                    }

                    R.drawable.image3 -> {
                        popFoodImageView.setImageResource(R.drawable.pop3_food)
                        popMenuImageView.setImageResource(R.drawable.pop3_menu)
                        latitude = 36.363496  // Location 3 latitude
                        longitude = 127.358706  // Location 3 longitude
                    }

                    R.drawable.image4 -> {
                        popFoodImageView.setImageResource(R.drawable.pop4_food)
                        popMenuImageView.setImageResource(R.drawable.pop4_menu)
                        latitude = 36.363423  // Location 4 latitude
                        longitude = 127.357545  // Location 4 longitude
                    }

                    R.drawable.image5 -> {
                        popFoodImageView.setImageResource(R.drawable.pop5_food)
                        popMenuImageView.setImageResource(R.drawable.pop5_menu)
                        latitude = 36.362481  // Location 5 latitude
                        longitude = 127.358189  // Location 5 longitude
                    }

                    R.drawable.image6 -> {
                        popFoodImageView.setImageResource(R.drawable.pop6_food)
                        popMenuImageView.setImageResource(R.drawable.pop6_menu)
                        latitude = 36.363142  // Location 6 latitude
                        longitude = 127.358720  // Location 6 longitude
                    }

                    R.drawable.image7 -> {
                        popFoodImageView.setImageResource(R.drawable.pop7_food)
                        popMenuImageView.setImageResource(R.drawable.pop7_menu)
                        latitude = 36.363557  // Location 7 latitude
                        longitude = 127.358625  // Location 7 longitude
                    }

                    R.drawable.image8 -> {
                        popFoodImageView.setImageResource(R.drawable.pop8_food)
                        popMenuImageView.setImageResource(R.drawable.pop8_menu)
                        latitude = 36.363037  // Location 8 latitude
                        longitude = 127.357801  // Location 8 longitude
                    }

                    R.drawable.image9 -> {
                        popFoodImageView.setImageResource(R.drawable.pop9_food)
                        popMenuImageView.setImageResource(R.drawable.pop9_menu)
                        latitude = 36.363325  // Location 9 latitude
                        longitude = 127.359104  // Location 9 longitude
                    }

                    R.drawable.image10 -> {
                        popFoodImageView.setImageResource(R.drawable.pop10_food)
                        popMenuImageView.setImageResource(R.drawable.pop10_menu)
                        latitude =  36.362434 // Location 10 latitude
                        longitude = 127.358119  // Location 10 longitude
                    }

                    R.drawable.image11 -> {
                        popFoodImageView.setImageResource(R.drawable.pop11_food)
                        popMenuImageView.setImageResource(R.drawable.pop11_menu)
                        latitude = 36.363507  // Location 11 latitude
                        longitude = 127.358159  // Location 11 longitude
                    }

                    R.drawable.image12 -> {
                        popFoodImageView.setImageResource(R.drawable.pop12_food)
                        popMenuImageView.setImageResource(R.drawable.pop12_menu)
                        latitude = 36.352701  // Location 12 latitude
                        longitude = 127.372166  // Location 12 longitude
                    }

                    R.drawable.image13 -> {
                        popFoodImageView.setImageResource(R.drawable.pop13_food)
                        popMenuImageView.setImageResource(R.drawable.pop13_menu)
                        latitude = 36.352612  // Location 13 latitude
                        longitude = 127.373712  // Location 13 longitude
                    }

                    R.drawable.image14 -> {
                        popFoodImageView.setImageResource(R.drawable.pop14_food)
                        popMenuImageView.setImageResource(R.drawable.pop14_menu)
                        latitude = 36.357402  // Location 14 latitude
                        longitude = 127.350244  // Location 14 longitude
                    }

                    R.drawable.image15 -> {
                        popFoodImageView.setImageResource(R.drawable.pop15_food)
                        popMenuImageView.setImageResource(R.drawable.pop15_menu)
                        latitude = 36.356487  // Location 15 latitude
                        longitude = 127.338124  // Location 15 longitude
                    }

                    R.drawable.image16 -> {
                        popFoodImageView.setImageResource(R.drawable.pop16_food)
                        popMenuImageView.setImageResource(R.drawable.pop16_menu)
                        latitude = 36.352596  // Location 16 latitude
                        longitude = 127.373417  // Location 16 longitude
                    }

                    R.drawable.image17 -> {
                        popFoodImageView.setImageResource(R.drawable.pop17_food)
                        popMenuImageView.setImageResource(R.drawable.pop17_menu)
                        latitude = 36.359874  // Location 17 latitude
                        longitude = 127.349795  // Location 17 longitude
                    }

                    R.drawable.image18 -> {
                        popFoodImageView.setImageResource(R.drawable.pop18_food)
                        popMenuImageView.setImageResource(R.drawable.pop18_menu)
                        latitude = 36.362025  // Location 18 latitude
                        longitude = 127.353429  // Location 18 longitude
                    }

                    R.drawable.image19 -> {
                        popFoodImageView.setImageResource(R.drawable.pop19_food)
                        popMenuImageView.setImageResource(R.drawable.pop19_menu)
                        latitude = 36.363032  // Location 19 latitude
                        longitude = 127.358227  // Location 19 longitude
                    }

                    R.drawable.image20 -> {
                        popFoodImageView.setImageResource(R.drawable.pop20_food)
                        popMenuImageView.setImageResource(R.drawable.pop20_menu)
                        latitude = 36.359423  // Location 20 latitude
                        longitude = 127.344466  // Location 20 longitude
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