package com.example.madcamp_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import java.util.Calendar
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Parcelable
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import android.widget.LinearLayout.LayoutParams

class ThreeFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventListView: ListView
    private var eventList: MutableMap<String, MutableList<String>> =mutableMapOf()
    var itemList=ArrayList<Phone>()
    var events:MutableList<String> =mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_three, container, false)

        // Find the necessary views
        calendarView = view.findViewById(R.id.calendarView)
        eventListView = view.findViewById(R.id.eventListView)
        val addEventButton = view.findViewById<Button>(R.id.addEventButton)


        //fragment1로부터 받은 데이터
        setFragmentResultListener("requestKey"){key, bundle->
            itemList = bundle.getParcelableArrayList("bundleKey")!!

        }
        if(savedInstanceState!= null){
            itemList= savedInstanceState.getParcelableArrayList("MyData")!!

            eventList = (savedInstanceState.getSerializable("eventList") as? HashMap<String, MutableList<String>>)?.toMutableMap() ?:mutableMapOf()
        }

        // Set the date click listener for the calendar view
        calendarView.setOnDateChangeListener{_, year, month, dayOfMonth->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"

            // Retrieve existing events for the selected date and display them in the event list view
            var events=getEventsForDate(selectedDate)
            if (events.isEmpty()) {
                eventListView.adapter= ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,arrayOf("No events"))
            } else {
                var adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,events)
                eventListView.adapter= adapter
            }
        }

        eventListView.setOnItemClickListener{
                parent,view,position,id->

            var selectedItem = view as TextView
            var selectedItem2 = selectedItem.text.toString()
            var selectedItem3 = selectedItem2.substring(6,selectedItem2.indexOf(","))
            var startIndex = selectedItem2.indexOf("Restaurant:") + "Restaurant:".length+1
            var endIndex = selectedItem2.indexOf(",", startIndex)
            var selectedItem4 = selectedItem2.substring(startIndex,endIndex)


            val view = inflater.inflate(R.layout.custom_dialog_edit,container,false)
            val edit = view.findViewById<Button>(R.id.edit)
            val delete = view.findViewById<Button>(R.id.delete)

            var et1:String=""
            var et2:String=""

            //spinnner
            var count = itemList.size
            var data2=mutableListOf("선택하세요")
            var data= mutableListOf("선택하세요","골목","잇마이타이","가배 커피바","삼부자 부대찌개","비스트로 퍼블릭","마쯔미","요시다","베리 신주쿠","맑음","하바COOK","BURGIZ","WOOTZ","ORANGE BLUES","태평소 국밥","일당 감자탕","와타요업","에이트","달구지 막창","정직한커피","스바라시라멘")
            while(count>0) {
                data2.add(itemList[count-1].name)
                count-=1
            }

            var adap = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,data)
            var adap2 = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,data2)

            val spinner = view.findViewById<Spinner>(R.id.Restaurant)
            val spinner2 = view.findViewById<Spinner>(R.id.Attendee)

            spinner.adapter=adap
            spinner2.adapter= adap2
            spinner.onItemSelectedListener= object:
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    et1=data[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    TODO("Not yet implemented")
                }
            }
            spinner2.onItemSelectedListener= object:
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    et2=data2[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    TODO("Not yet implemented")
                }
            }

            var events = getEventsForDate(selectedItem3)
            val builder = Dialog(requireContext())
            builder.setContentView(view)
            builder.show()
            builder.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
               edit.setOnClickListener {

                   val index = events.indexOf(selectedItem2)
                   if (events.isNotEmpty()) {
                       if (index != -1) {
                           // Update the event here
                           val newEvent =
                               "Date: $selectedItem3, Restaurant: $et1, Attendee: $et2"
                           events[index] = newEvent
                           updateEventListView(selectedItem3)
                           Toast.makeText(
                               requireContext(),
                               "일정이 변경되었습니다",
                               Toast.LENGTH_SHORT
                           ).show()
                       } else {
                           // 해당 요소가 리스트에 없는 경우에 대한 처리
                           val count = events.size
                           Toast.makeText(
                               requireContext(),
                               "$count 해당 요소를 찾을 수 없습니다.",
                               Toast.LENGTH_SHORT
                           ).show()
                       }
                   } else {
                       // 리스트가 비어있는 경우에 대한 처리
                   }
                   builder.dismiss()
               }
                delete.setOnClickListener {
                    if (events.isNotEmpty()) {
                        val index = events.indexOf(selectedItem2) // 현재 값을 가진 요소의 인덱스 찾기

                        if (index != -1) {

                            selectedItem3 = selectedItem2.substring(6, selectedItem2.indexOf(","))
                            events = getEventsForDate(selectedItem3)

                            events.remove(selectedItem2)

                            var adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_list_item_1,
                                events
                            )
                            eventListView.adapter = adapter
                        } else {
                            // 해당 요소가 리스트에 없는 경우에 대한 처리
                            Toast.makeText(
                                requireContext(),
                                "해당 요소를 찾을 수 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // 리스트가 비어있는 경우에 대한 처리
                        Toast.makeText(requireContext(), "리스트가 비어있습니다.", Toast.LENGTH_SHORT).show()
                    }
                    builder.dismiss()
                }
        }

// Set the click listener for the add event button
        addEventButton.setOnClickListener{
            showAddEventDialog()
        }
        val result = eventList
        setFragmentResult("requestKey2",bundleOf("bundleKey" to result))
        parentFragmentManager.beginTransaction()

        updateEventListViewForAllDates()

        // Get the layout params of the add event button
        val layoutParams = addEventButton.layoutParams as ViewGroup.MarginLayoutParams

        // Set the left margin to move the button to the right
        val marginInPixels = resources.getDimensionPixelSize(R.dimen.button_margin_left)
        layoutParams.leftMargin = marginInPixels

        // Set the top margin to move the button down
        val topMarginInPixels = resources.getDimensionPixelSize(R.dimen.button_margin_top)
        layoutParams.topMargin = topMarginInPixels

        // Set the modified layout params to the add event button
        addEventButton.layoutParams = layoutParams

        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("MyData",itemList)
        //outState.putParcelableArrayList("MyDate2",events)
        outState.putSerializable("eventList", HashMap(eventList))
    }


    fun refreshAdapter(adapter: ArrayAdapter<String>, items: MutableList<String>) {
        adapter.clear()
        adapter.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun updateEventListViewForAllDates() {
        // Clear the event list view
        eventListView.adapter = null

        // Iterate over all dates in eventList
        for (selectedDate in eventList.keys) {
            val events = eventList[selectedDate] ?: emptyList<String>()

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
            eventListView.adapter = adapter
        }
    }
    private fun getEventsForDate(date: String): MutableList<String> {
        return eventList[date] ?:mutableListOf()
    }


    private fun showAddEventDialog() {

        val dialogView =layoutInflater.inflate(R.layout.dialog_add_event, null)

        val datePickerButton = dialogView.findViewById<Button>(R.id.datePickerButton)

        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        var restaurantName = ""
        var attendeeName = ""
        var attendee: String = ""
        var restaurant: String = ""
        //spinnner
        var itemList = ArrayList<Phone>()

        // Fragment result api 사용해서 프래그먼트1으로부터 데이터 전달

        var count = itemList.size
        var data2=mutableListOf("선택하세요")
        var data= mutableListOf("선택하세요","골목","잇마이타이","가배 커피바","삼부자 부대찌개","비스트로 퍼블릭","마쯔미","요시다","베리 신주쿠","맑음","하바COOK","BURGIZ","WOOTZ","ORANGE BLUES","태평소 국밥","일당 감자탕","와타요업","에이트","달구지 막창","정직한커피","스바라시라멘")
        while(count>0) {
            data2.add(itemList[count-1].name)
            count-=1
        }

        var adap =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, data)
        var adap2 =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, data2)
        val spinner2 = dialogView.findViewById<Spinner>(R.id.attendeeNameEditText)
        val spinner = dialogView.findViewById<Spinner>(R.id.restaurantNameEditText)
        spinner.adapter= adap
        spinner2.adapter= adap2

        spinner.onItemSelectedListener= object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restaurant = data[position]
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restaurant = data[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        spinner2.onItemSelectedListener= object :
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                attendee = data2[position]
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                attendee = data2[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        datePickerButton.setOnClickListener{
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                {_, selectedYear, selectedMonth, selectedDayOfMonth->
                    restaurantName = restaurant
                    attendeeName = attendee
                    year = selectedYear
                    month = selectedMonth
                    dayOfMonth = selectedDayOfMonth
                },
                year, month, dayOfMonth
            )
            datePickerDialog.show()
        }
        val builder = Dialog(requireContext())
        builder.setContentView(dialogView)
        builder.show()
        builder.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val ok = dialogView.findViewById<Button>(R.id.ok)
        val cancel = dialogView.findViewById<Button>(R.id.cancel)

        // cancel 버튼 이벤트
        cancel.setOnClickListener{
                builder.dismiss()
            }

        //ok 버튼 이벤트 -> 일정 추가
        ok.setOnClickListener{
                if (restaurantName.isNotBlank() && attendeeName.isNotBlank()) {
                    // Save the event (restaurant name and attendee name) to the selected date
                    saveEvent(year, month, dayOfMonth, restaurantName, attendeeName)

                    // Update the event list view
                    updateEventListView("$dayOfMonth/${month + 1}/$year")
                    Toast.makeText(requireContext(), "일정이 등록되었습니다", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "일정을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                builder.dismiss()
            }


    }

    private fun updateEventListView(date: String) {
        val events = getEventsForDate(date)
        if (events.isEmpty()) {
            eventListView.adapter= ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,arrayOf("No events"))
        } else {
            eventListView.adapter= ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
        }
    }

    private fun saveEvent(year: Int, month: Int, dayOfMonth: Int, restaurantName: String, attendeeName: String) {
        val selectedDate = "$dayOfMonth/${month + 1}/$year"
        val event = "Date: $selectedDate, Restaurant: $restaurantName, Attendee: $attendeeName"

        // Save the event to the selected date
        val events = eventList[selectedDate]?.toMutableList() ?:mutableListOf()
        events.add(event)
        eventList[selectedDate] = events

        // Update the event list view
        updateEventListView(selectedDate)
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        return "$day/$month/$year"
    }
}





