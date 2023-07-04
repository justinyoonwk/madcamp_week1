package com.example.madcamp_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import java.util.Calendar
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.res.AssetManager
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject

class ThreeFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventListView: ListView
    private val eventList: MutableMap<String, MutableList<String>> = mutableMapOf()

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
        var itemList = ArrayList<Phone>()

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

        // Set the date click listener for the calendar view
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"

            // Retrieve existing events for the selected date and display them in the event list view
            var events=getEventsForDate(selectedDate)
            if (events.isEmpty()) {
                eventListView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayOf("No events"))
            } else {
                var adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,events)
                eventListView.adapter = adapter
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

            var et1:String=""
            var et2:String=""

            //spinnner

            var count = itemList.size
            var data2=mutableListOf("선택하세요")
            var data= mutableListOf("선택하세요","골목","잇마이타이","가배 커피바","삼부자 부대찌개","PUBLIC","마쯔미","요시다","VERY SINJUKU","Malgm","하바COOK","BURGIZ","WOOTZ","ORANGE BLUE","태평소 국밥","뼈다구 감자탕","와타요집","COFFEE","달구지막창","정직한커피","스바라시라멘")
            while(count>0){
                data2.add(itemList[count-1].name)
                count-=1
            }

            var adap = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,data)
            var adap2 = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,data2)

            val spinner = view.findViewById<Spinner>(R.id.Restaurant)
            val spinner2 = view.findViewById<Spinner>(R.id.Attendee)

            spinner.adapter =adap
            spinner2.adapter = adap2
            spinner.onItemSelectedListener = object:
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
            spinner2.onItemSelectedListener = object:
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
            val builder = AlertDialog.Builder(requireContext())
                .setTitle("수정 혹은 삭제")
                .setView(view)
                .setNegativeButton("수정",
                    DialogInterface.OnClickListener { dialog, which ->
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
                                Toast.makeText(requireContext(), "$count 해당 요소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else {
                            // 리스트가 비어있는 경우에 대한 처리
                            /*
                            val newValue = selectedItem2.substring(0, 14) + et.text.toString() + "," + et2.text.toString()
                            events.add(newValue)
                            selectedItem2=newValue
                            Toast.makeText(requireContext(),"$events,events",Toast.LENGTH_LONG).show()

                            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
                            eventListView.adapter=adapter

                            Toast.makeText(requireContext(), "리스트가 비어있습니다.", Toast.LENGTH_SHORT).show()
                            val count = events.size

                            Toast.makeText(requireContext(),"$count",Toast.LENGTH_SHORT).show()
                        */
                        }

                    })
                .setPositiveButton("삭제",
                    DialogInterface.OnClickListener { dialog, which ->

                        if (events.isNotEmpty()) {
                            val index = events.indexOf(selectedItem2) // 현재 값을 가진 요소의 인덱스 찾기

                            if (index != -1) {

                                selectedItem3=selectedItem2.substring(6,selectedItem2.indexOf(","))
                                events = getEventsForDate(selectedItem3)

                                events.remove(selectedItem2)

                                var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
                                eventListView.adapter = adapter
                            } else {
                                // 해당 요소가 리스트에 없는 경우에 대한 처리
                                Toast.makeText(requireContext(), "해당 요소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // 리스트가 비어있는 경우에 대한 처리
                            Toast.makeText(requireContext(), "리스트가 비어있습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }).show()
        }

        // Set the click listener for the add event button
        addEventButton.setOnClickListener {
            showAddEventDialog()
        }

        return view
    }
    fun refreshAdapter(adapter: ArrayAdapter<String>, items: MutableList<String>) {
        adapter.clear()
        adapter.addAll(items)
        adapter.notifyDataSetChanged()
    }

    private fun getEventsForDate(date: String): MutableList<String> {
        return eventList[date] ?: mutableListOf()
    }

    private fun showAddEventDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_event, null)

        val datePickerButton = dialogView.findViewById<Button>(R.id.datePickerButton)

        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        var restaurantName = ""
        var attendeeName = ""
        var attendee:String=""
        var restaurant : String =""
        //spinnner
        var itemList = ArrayList<Phone>()

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

        var count = itemList.size
        var data2=mutableListOf("선택하세요")
        var data= mutableListOf("선택하세요","골목","잇마이타이","가배 커피바","삼부자 부대찌개","PUBLIC","마쯔미","요시다","VERY SINJUKU","Malgm","하바COOK","BURGIZ","WOOTZ","ORANGE BLUE","태평소 국밥","뼈다구 감자탕","와타요집","COFFEE","달구지막창","정직한커피","스바라시라멘")
        while(count>0){
            data2.add(itemList[count-1].name)
            count-=1
        }

        var adap = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,data)
        var adap2 = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,data2)
        val spinner2 = dialogView.findViewById<Spinner>(R.id.attendeeNameEditText)
        val spinner = dialogView.findViewById<Spinner>(R.id.restaurantNameEditText)
        spinner.adapter =adap
        spinner2.adapter=adap2

        spinner.onItemSelectedListener = object:
            AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restaurant=data[position]

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restaurant=data[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        spinner2.onItemSelectedListener = object:
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
                attendee=data2[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        datePickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
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

        dialogBuilder.setView(dialogView)
            .setTitle("Add Event")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("OK") { dialog, _ ->
                if (restaurantName.isNotBlank() && attendeeName.isNotBlank()) {
                    // Save the event (restaurant name and attendee name) to the selected date
                    saveEvent(year, month, dayOfMonth, restaurantName, attendeeName)

                    // Update the event list view
                    updateEventListView("$dayOfMonth/${month + 1}/$year")
                    Toast.makeText(requireContext(), "일정이 등록되었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "일정을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    private fun updateEventListView(date: String) {
        val events = getEventsForDate(date)
        if (events.isEmpty()) {
            eventListView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayOf("No events"))
        } else {
            eventListView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
        }
    }

    private fun saveEvent(year: Int, month: Int, dayOfMonth: Int, restaurantName: String, attendeeName: String) {
        val selectedDate = "$dayOfMonth/${month + 1}/$year"
        val event = "Date: $selectedDate, Restaurant: $restaurantName, Attendee: $attendeeName"

        // Save the event to the selected date
        val events = eventList[selectedDate]?.toMutableList() ?: mutableListOf()
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


