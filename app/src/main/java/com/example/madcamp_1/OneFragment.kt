package com.example.madcamp_1


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileDescriptor
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.io.PrintWriter
import java.lang.System.exit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
val PERMISSION_REQUEST_CODE = 123
/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneFragment : Fragment() {

    private lateinit var dialogImageView: ImageView
    private lateinit var loadImage: ActivityResultLauncher<String>

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
     var filePath:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //for image load
        loadImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {

                filePath=uriToPath(requireContext(),uri)
                Glide.with(requireContext())
                    .load(uri) // 이미지 URl
                    .into(dialogImageView) // 이미지를 표시할 ImageView
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_one, container, false)
        val rv = v.findViewById<RecyclerView>(R.id.rv)
        val itemList = ArrayList<Phone>()

        // json parsing
        val assetManager:AssetManager = requireContext().resources.assets
        val inputStream= assetManager.open("data.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val jObject = JSONObject(jsonString)
        val jArray = jObject.getJSONArray("data")

        for (index in 0 until jArray.length()) {
            val jsonObject = jArray.getJSONObject(index)

        val Data = Phone(
             jsonObject.getString("name"),
             jsonObject.getString("phone_Number"),
             jsonObject.getString("fav_Food"),
             jsonObject.getString("dis_Food"),
             jsonObject.getString("address"),
            jsonObject.getString("photo")
        )
            itemList.add(Data)
        }


        val phoneAdapter = PhoneAdapter(itemList)

// 리사이클러뷰 리스트 클릭 시 추가 정보 다이얼로그
        phoneAdapter.itemClickListener = object: PhoneAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

                val builder = AlertDialog.Builder(v.context)
                val dialogView =
                    LayoutInflater.from(v.context).inflate(R.layout.custom_dialog_info, null)

                val dialogName = dialogView.findViewById<EditText>(R.id.name)
                dialogName.setText(itemList[position].name)

                val dialogPhone = dialogView.findViewById<EditText>(R.id.phone_Number)
                dialogPhone.setText(itemList[position].phone_Number)

                val dialogFavFood = dialogView.findViewById<EditText>(R.id.fav_Food)
                dialogFavFood.setText(itemList[position].fav_Food)

                val dialogDisFood = dialogView.findViewById<EditText>(R.id.dis_Food)
                dialogDisFood.setText(itemList[position].dis_Food)

                val photo_item: String = itemList[position].photo // 리소스 식별자

                dialogImageView = dialogView.findViewById<ImageView>(R.id.imageView)
/*
                val resourceName = photo_item?.substringAfterLast(".")
                val resourceId: Int = requireContext().resources.getIdentifier(resourceName, "drawable", requireContext().packageName)
                dialogImageView.setImageResource(resourceId)
*/


                fun isGalleryImage(uri: Uri): Boolean {
                    return uri.scheme == "content"
                }

                fun isDrawableImage(uri: Uri): Boolean {
                    return uri.scheme == "android.resource"
                }

                val uri: Uri = if (photo_item?.startsWith("R.drawable") == true) {
                    val resourceName = photo_item.substringAfterLast(".")
                    val resourceId: Int = requireContext().resources.getIdentifier(
                        resourceName,
                        "drawable",
                        requireContext().packageName
                    )
                    Uri.parse("android.resource://${ requireContext().packageName}/$resourceId")
                } else {
                    photo_item!!.toUri()
                }
                val isGalleryImage = isGalleryImage(uri)
                val isDrawableImage = isDrawableImage(uri)

                if (isGalleryImage) {
                    // 갤러리에서 가져온 이미지인 경우 처리

                    Glide.with(requireContext())
                        .load(photo_item)
                        .into(dialogImageView)

                } else if (isDrawableImage) {
                    // Drawable에 있는 파일인 경우 처리
//Success
                    val resourceName = photo_item.substringAfterLast(".")
                    val resourceId: Int = requireContext().resources.getIdentifier(resourceName, "drawable",requireContext().packageName)
                    dialogImageView.setImageResource(resourceId)
                } else {
                    // 다른 스키마인 경우 처리
                }

                //For contact with gallery app
                dialogImageView.setOnClickListener{
                    loadImage.launch("image/*")

                }


                phoneAdapter.notifyDataSetChanged()

                val dialogAddress = dialogView.findViewById<EditText>(R.id.address)
                dialogAddress.setText(itemList[position].address)
                builder.setView(dialogView)
                    .setPositiveButton("수정") { dialogInterface, i ->
                        itemList[position].name=dialogName.text.toString()
                        itemList[position].phone_Number=dialogPhone.text.toString()
                        itemList[position].fav_Food=dialogFavFood.text.toString()
                        itemList[position].dis_Food = dialogDisFood.text.toString()
                        itemList[position].address=dialogAddress.text.toString()
                        itemList[position].photo=filePath
                        phoneAdapter.notifyDataSetChanged()

                    }
                    .setNegativeButton("삭제") { dialogInterface, i ->
                        // 취소 버튼 클릭 시 수행할 작업

                    }
                    .show()
            }

        }

// 연락처 추가 버튼
        val add_btn = v.findViewById<FloatingActionButton>(R.id.add_btn)


        add_btn.setOnClickListener{
        val builder = AlertDialog.Builder(context)
        val DialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

        builder.setView(DialogView)
            .setPositiveButton("확인") { dialogInterface, i ->
                val dialogName = DialogView.findViewById<EditText>(R.id.name).text.toString()
                val dialogPhone =
                    DialogView.findViewById<EditText>(R.id.phone_Number).text.toString()
                val dialogFavFood = DialogView.findViewById<EditText>(R.id.fav_Food).text.toString()
                val dialogDisFood = DialogView.findViewById<EditText>(R.id.dis_Food).text.toString()
                val dialogAddress = DialogView.findViewById<EditText>(R.id.address).text.toString()
                val dialogPhoto = DialogView.findViewById<ImageView>(R.id.img_load)

                val dialogFile = filePath
                val new_uri = filePath!!.toUri()

                Glide.with(requireContext())
                    .load(new_uri)
                    .into(dialogPhoto)

                //For contact with gallery app
                dialogPhoto.setOnClickListener{
                    loadImage.launch("image/*")

                }


                phoneAdapter.notifyDataSetChanged()

                phoneAdapter.addItem(
                    Phone(
                        dialogName,
                        dialogPhone,
                        dialogFavFood,
                        dialogDisFood,
                        dialogAddress,
                        dialogFile
                    )
                )


            }
            .setNegativeButton("취소") { dialogInterface, i ->
                /* 취소일 때 아무 액션이 없으므로 빈칸 */
            }
            .show()
    }

            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv.layoutManager = layoutManager

            rv.adapter = phoneAdapter

            return v



    }
    fun getImageFilePath(uri: Uri):String {
        // if (uri.scheme == "content") { //In Gallery
        val context = requireContext()
        val contentResolver = context.contentResolver

        // Create file path inside app's data dir
        val filePath = (context.applicationInfo.dataDir + File.separator
                + System.currentTimeMillis())
        val file = File(filePath)

        return file.getAbsolutePath()
        }

    fun uriToPath(context: Context, contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        cursor?.use {
            it.moveToNext()
            val path = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))

            return path
        }
        return ""
    }


/*
val projection = arrayOf(MediaStore.Images.Media.DATA)
    val c = requireContext().contentResolver.query(uri, projection, null, null, null)

    var index = c!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    c.moveToFirst()

    var result = c.getString(index)

    return result
*/

    companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment OneFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                OneFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
    }

