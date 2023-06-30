package com.example.madcamp_1

import android.app.AlertDialog
import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


public class PhoneAdapter(val itemList: ArrayList<Profile>): RecyclerView.Adapter<PhoneAdapter.BoardViewHolder>(){

    var mPosition =0
    fun getPosition():Int{
        return mPosition
    }

    fun setPosition(position:Int){
        mPosition=position
    }

    fun addItem(data:Profile){
        itemList.add(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhoneAdapter.BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview,parent,false)
        return BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.phone_Number.text = itemList[position].phone_Number.toString()

        holder.itemView.setOnClickListener { view ->
            Toast.makeText(view.context, "$position 아이템 클릭", Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(view.context)
            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.custom_dialog_info, null)

            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    val dialogName = dialogView.findViewById<EditText>(R.id.name).text.toString()
                    val dialogPhone =
                        dialogView.findViewById<EditText>(R.id.phone_Number).text.toString()

                    // 다이얼로그에서 입력한 값(dialogName, dialogPhone)을 사용하여 원하는 작업 수행

                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    // 취소 버튼 클릭 시 수행할 작업
                }
                .show()
        }
    }

        override fun getItemCount(): Int {
       return itemList.count()
    }
    inner class BoardViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.name)
        val phone_Number=itemView.findViewById<TextView>(R.id.phone_Number)
    }

    }
