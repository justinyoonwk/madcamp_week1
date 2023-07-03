package com.example.madcamp_1

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CalAdapter(val context: Context, val calList :MutableList<String>): BaseAdapter() {

    var mPosition = 0
    fun getPosition(): Int {
        return mPosition
    }

    fun setPosition(position: Int) {
        mPosition = position
    }

    fun removeItem(position: Int) {
        if (position > 0) {
            calList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getItem(position: Int): Any {
        return calList.size
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, null)

        val event = view.findViewById<TextView>(R.id.event)
        event.text = calList[position]
        return view

    }/*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalAdapter.BoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return CalAdapter.BoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalAdapter.BoardViewHolder, position: Int) {
        holder.event.text = calList[position]
        holder.itemView.setOnClickListener { view ->
            Toast.makeText(view.context, "$position 아이템 클릭", Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(view.context)
            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.list_item, null)

            builder.setView(dialogView)
                .setPositiveButton("삭제") { dialogInterface, i ->

                    // 다이얼로그에서 입력한 값(dialogName, dialogPhone)을 사용하여 원하는 작업 수행

                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    // 취소 버튼 클릭 시 수행할 작업
                }
                .show()
        }


    }

    override fun getItemCount(): Int {
        return calList.size
    }
    inner class BoardViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val event = itemView.findViewById<TextView>(R.id.event)
    }
}*/
}