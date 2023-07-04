package com.example.madcamp_1


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneFragment : Fragment() {

    private lateinit var dialogImageView: ImageView
    private lateinit var dialogPhoto:ImageView
    private lateinit var loadImage: ActivityResultLauncher<String>
    private lateinit var loadImage2: ActivityResultLauncher<String>

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
                filePath = getImageFilePathFromGallery(uri)
                Glide.with(requireContext())
                    .load(uri) // 이미지 URl
                    .into(dialogImageView) // 이미지를 표시할 ImageView
            }
        }
        loadImage2 =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    filePath=getImageFilePathFromGallery(uri)
                    Glide.with(requireContext())
                        .load(uri) // 이미지 URl
                        .into(dialogPhoto) // 이미지를 표시할 ImageView
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
        val assetManager: AssetManager = requireContext().resources.assets
        val inputStream = assetManager.open("data.json")
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

        phoneAdapter.itemClickListener = object : PhoneAdapter.onItemClickListener {
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

                dialogPhoto = dialogView.findViewById<ImageView>(R.id.imageView)

                fun isGalleryImage2(filePath: String): Boolean {
                    return filePath.startsWith(Environment.getExternalStorageDirectory().path)
                }

                if (isGalleryImage2(photo_item)) {
                    // 갤러리에서 가져온 이미지인 경우 처리

                    Glide.with(requireContext())
                        .load(File(photo_item))
                        .into(dialogPhoto)

                } else {
                    // Drawable에 있는 파일인 경우 처리

                    val resourceName = photo_item.substringAfterLast(".")
                    val resourceId: Int = requireContext().resources.getIdentifier(
                        resourceName,
                        "drawable",
                        requireContext().packageName
                    )
                    dialogPhoto.setImageResource(resourceId)
                }

                //For contact with gallery app
                dialogPhoto.setOnClickListener {
                    loadImage2.launch("image/*")
                    Toast.makeText(requireContext(), "성공?", Toast.LENGTH_LONG).show()

                }
                Toast.makeText(requireContext(), "$photo_item", Toast.LENGTH_LONG).show()
                phoneAdapter.notifyDataSetChanged()

                val dialogAddress = dialogView.findViewById<EditText>(R.id.address)
                dialogAddress.setText(itemList[position].address)


                builder.setView(dialogView)
                    .setPositiveButton("수정") { dialogInterface, i ->
                        itemList[position].name = dialogName.text.toString()
                        itemList[position].phone_Number = dialogPhone.text.toString()
                        itemList[position].fav_Food = dialogFavFood.text.toString()
                        itemList[position].dis_Food = dialogDisFood.text.toString()
                        itemList[position].address = dialogAddress.text.toString()
                        if(filePath != "") { //안그러면 글자만 수정해도 이전에 갤러리에서 선택했던 이미지로 이미지뷰가 설정됨
                            itemList[position].photo = filePath
                        }else{
                            itemList[position].photo = itemList[position].photo
                        }
                        phoneAdapter.notifyDataSetChanged()
                        filePath=""

                    }
                    .setNegativeButton("삭제") { dialogInterface, i ->
                        // 취소 버튼 클릭 시 수행할 작업
                        itemList.removeAt(position)
                        phoneAdapter.notifyDataSetChanged()
                    }
                    .show()
            }

        }


// 연락처 추가 버튼
        val add_btn = v.findViewById<FloatingActionButton>(R.id.add_btn)

        add_btn.setOnClickListener{

        val builder = AlertDialog.Builder(context)
            val DialogView =
                LayoutInflater.from(v.context).inflate(R.layout.custom_dialog, null)
            dialogImageView = DialogView.findViewById<ImageView>(R.id.image1)
            dialogImageView.setOnClickListener {
                Toast.makeText(v.context,"성공?",Toast.LENGTH_LONG).show()
                loadImage.launch("image/*")
            }
            builder.setView(DialogView)
            .setPositiveButton("확인") { dialogInterface, i ->
                val dialogName = DialogView.findViewById<EditText>(R.id.name).text.toString()
                val dialogPhone =
                    DialogView.findViewById<EditText>(R.id.phone_Number).text.toString()
                val dialogFavFood = DialogView.findViewById<EditText>(R.id.fav_Food).text.toString()
                val dialogDisFood = DialogView.findViewById<EditText>(R.id.dis_Food).text.toString()
                val dialogAddress = DialogView.findViewById<EditText>(R.id.address).text.toString()
                dialogImageView = DialogView.findViewById(R.id.image1)
                val dialogImage: String=filePath
                fun isGalleryImage2(filePath: String): Boolean {
                    return filePath.startsWith(Environment.getExternalStorageDirectory().path)
                }

                if (isGalleryImage2(dialogImage)) {
                    // 갤러리에서 가져온 이미지인 경우 처리
                    Glide.with(v.context)
                        .load(File(dialogImage))
                        .into(dialogImageView)

                } else {
                    // Drawable에 있는 파일인 경우 처리
                    val resourceName = dialogImage.substringAfterLast(".")
                    val resourceId: Int = v.context.resources.getIdentifier(resourceName, "drawable",requireContext().packageName)
                    dialogImageView.setImageResource(resourceId)
                }
                //갤러리 접근

                //갱신 처리
                phoneAdapter.notifyDataSetChanged()

                phoneAdapter.addItem(
                    Phone(
                        dialogName,
                        dialogPhone,
                        dialogFavFood,
                        dialogDisFood,
                        dialogAddress,
                        dialogImage
                    )
                )
            }
            .setNegativeButton("취소") { dialogInterface, i ->
                // 취소일 때 아무 액션이 없으므로 빈칸
            }
            .show()
    }
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rv.layoutManager = layoutManager
            rv.adapter = phoneAdapter
            return v

    }

    //갤러리 이미지 경로를 찾아오는 코드
    fun getImageFilePathFromGallery(uri: Uri): String {
        val context = requireContext()
        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val fileName = it.getString(columnIndex)
                val one = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.absolutePath
                val filePath = "/storage/emulated/0/Download/$fileName"

                return filePath
            }
        }
        return ""
    }


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

