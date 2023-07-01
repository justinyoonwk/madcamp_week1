package com.example.madcamp_1


import android.app.AlertDialog
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_one, container, false)

        val rv = v.findViewById<RecyclerView>(R.id.rv)

        val itemList = ArrayList<Profile>()
        itemList.add(Profile("김민희", "77141619"))
        itemList.add(Profile("김김김", "11111111"))
        itemList.add(Profile("이이이", "22222222"))
        itemList.add(Profile("손손손", "33333333"))
        itemList.add(Profile("박박박", "44444444"))
        val phoneAdapter = PhoneAdapter(itemList)

        val add_btn = v.findViewById<Button>(R.id.add_btn)


        add_btn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    val dialogName = dialogView.findViewById<EditText>(R.id.name).text.toString()
                    val dialogPhone = dialogView.findViewById<EditText>(R.id.phone_Number).text.toString()

                    phoneAdapter.addItem(Profile(dialogName, dialogPhone))
                    /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */

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

