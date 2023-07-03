package com.example.madcamp_1

import android.content.ContentUris
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.net.toUri

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.File


public class PhoneAdapter(var itemList: ArrayList<Phone>): RecyclerView.Adapter<PhoneAdapter.BoardViewHolder>() {


    interface onItemClickListener {
        fun onItemClick(position: Int) {}
    }

    //instance
    var itemClickListener: onItemClickListener? = null
    private lateinit var launcher: ActivityResultLauncher<Intent>
    var mPosition = 0
    fun getPosition(): Int {
        return mPosition
    }

    fun setPosition(position: Int) {
        mPosition = position
    }

    fun addItem(data: Phone) {
        itemList.add(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhoneAdapter.BoardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)

        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {

        holder.name.text = itemList[position].name
        holder.phone_Number.text = itemList[position].phone_Number
        val photo_item: String = itemList[position].photo

        //uri = photo_item!!.toUri()
        //val scheme=uri.scheme

        fun isGalleryImage(uri: Uri): Boolean {
            return uri.scheme == "content"
        }

        fun isDrawableImage(uri: Uri): Boolean {
            return uri.scheme == "android.resource"
        }
        fun filePathToUri(filePath: String): Uri? {
            return Uri.fromFile(File(filePath))
        }
        fun pathToUri(context: Context, filePath: String): Uri? {
            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                "_data = '$filePath'",
                null,
                null
            )
            val uri2 = Uri.parse(filePath)
            val filepath = uri2.getPath()

            cursor?.use {
                if (it.moveToNext()) {
                    val id = it.getInt(it.getColumnIndex("_id"))
                    return ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toLong()
                    )
                }
            }
            return null
        }


        val uri: Uri = if (photo_item?.startsWith("R.drawable") == true) {
            val resourceName = photo_item.substringAfterLast(".")
            val resourceId: Int = holder.itemView.context.resources.getIdentifier(
                resourceName,
                "drawable",
                holder.itemView.context.packageName
            )
            Uri.parse("android.resource://${holder.itemView.context.packageName}/$resourceId")
        } else ({
            if(photo_item != null){
                filePathToUri(photo_item)

            }else{

            }
        }) as Uri

        val isGalleryImage = isGalleryImage(uri)
        val isDrawableImage = isDrawableImage(uri)

        if (isGalleryImage) {
            // 갤러리에서 가져온 이미지인 경우 처리

            Glide.with(holder.itemView.context)
                .load(photo_item)
                .into(holder.photo)

        } else if (isDrawableImage) {
            // Drawable에 있는 파일인 경우 처리
//Success
            val resourceName = photo_item!!.substringAfterLast(".")
            val resourceId: Int = holder.itemView.context.resources.getIdentifier(resourceName, "drawable", holder.itemView.context.packageName)
            holder.photo.setImageResource(resourceId)
        } else {
            // 다른 스키마인 경우 처리
        }


            /*
        if (scheme == "http" || scheme == "https") {
            // 외부 라이브러리 URI인 경우 처리
            Glide.with(holder.itemView.context)
                .load(photo_item)
                .into(holder.photo)
        } else if (scheme == "file") {
            val resourceName = photo_item?.substringAfterLast(".")
            val resourceId: Int = holder.itemView.context.resources.getIdentifier(resourceName, "drawable", cont.packageName)
            holder.photo.setImageResource(resourceId)
        } else {
            // 다른 스키마인 경우 처리
        }
*/
        }
        /*
    fun bind(phone : Phone,context: Context){

        if(phone.photo != "") {
            val resourceId =
                context.resources.getIdentifier(phone.photo, "drawable", context.packageName)
            if (resourceId > 0) {
                photo.setImageResource(resourceId)
            } else {
                photo.setImageResource(R.drawable.image1)
            }
        }else{
                photo.setImageResource(R.drawable.image1)
            }

        if(phone.photo != ""){
            photo.setImageResource(phone.photo)
        }

    }*/

        override fun getItemCount(): Int {
            return itemList.count()
        }

        inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name = itemView.findViewById<TextView>(R.id.name)
            val phone_Number = itemView.findViewById<TextView>(R.id.phone_Number)
            val photo = itemView.findViewById<ImageView>(R.id.userImg)


            init {
                itemView.setOnClickListener {
                    itemClickListener?.onItemClick(adapterPosition) //By adapterPosition
                }
            }

        }

    }

// Uri -> Path (파일경로)
fun uriToPath(context: Context, contentUri: Uri): String {
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(contentUri, proj, null, null, null)
    cursor?.use {
        it.moveToNext()
        val path = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
        val uri = Uri.fromFile(File(path))
        return path
    }
    return ""
}
