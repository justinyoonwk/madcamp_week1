/*
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
        val view2 = inflater.inflate(R.layout.list_item,container,false)

        // Find the necessary views
        calendarView = view.findViewById(R.id.calendarView)
        eventListView = view.findViewById(R.id.eventListView)
        val addEventButton = view.findViewById<Button>(R.id.addEventButton)

        var event = getEventsForDate("01/07/2023")
        var adapter = CalAdapter(requireContext(),event)


        /*fun someFunction(){
            calAdapter.notifyDataSetChanged()
        }*/

        // Set the date click listener for the calendar view
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"

            // Retrieve existing events for the selected date and display them in the event list view
            val events = getEventsForDate(selectedDate)
            if (events.isEmpty()) {

            } else {
                eventListView.adapter = CalAdapter(requireContext(),events)
               // someFunction()
            }


        }
        //문제 -> caladapter가 전역변수가 아님
        //선택한 listView에서 selectedDate의 값을 가져온다
        //

        val del =view2.findViewById<Button>(R.id.del)
        del.setOnClickListener{
            adapter.removeItem(adapter.getPosition())//불러올수없음
        }


        // Set the click listener for the add event button
        addEventButton.setOnClickListener {
            showAddEventDialog()
        }

        return view
    }

    private fun getEventsForDate(date: String): MutableList<String> {
        return eventList[date]?.toMutableList() ?: mutableListOf()
    }


    private fun showAddEventDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_event, null)
        val restaurantNameEditText = dialogView.findViewById<EditText>(R.id.restaurantNameEditText)
        val attendeeNameEditText = dialogView.findViewById<EditText>(R.id.attendeeNameEditText)
        val datePickerButton = dialogView.findViewById<Button>(R.id.datePickerButton)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val restaurantName = restaurantNameEditText.text.toString()
                    val attendeeName = attendeeNameEditText.text.toString()

                    // Save the event (restaurant name and attendee name) to the selected date
                    saveEvent(selectedYear, selectedMonth, selectedDayOfMonth, restaurantName, attendeeName)

                    // Update the event list view
                    updateEventListView("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
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
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    private fun updateEventListView(date: String) {
        val events = getEventsForDate(date)
        if (events.isEmpty()) {

        } else {
            eventListView.adapter = CalAdapter(requireContext(), events)
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
*/

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
import android.view.Gravity
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext

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
        /*
            var events = getEventsForDate(selectedItem3)

            events.remove(selectedItem2)

            var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
            eventListView.adapter = adapter*/
        eventListView.setOnItemClickListener{
            parent,view,position,id->

            var selectedItem = view as TextView
            var selectedItem2 = selectedItem.text.toString()
            var selectedItem3 = selectedItem2.substring(6,selectedItem2.indexOf(","))
            var startIndex = selectedItem2.indexOf("Restaurant:") + "Restaurant:".length+1
            var endIndex = selectedItem2.indexOf(",", startIndex)
            var selectedItem4 = selectedItem2.substring(startIndex,endIndex)

            val startIndex2 = selectedItem2.indexOf("Attendee:") + "Attendee:".length
            val endIndex2 = selectedItem2.length
            val selectedItem5= selectedItem2.substring(startIndex2, endIndex2).trim()


            val view = inflater.inflate(R.layout.custom_dialog_edit,container,false)

            val et = view.findViewById<EditText>(R.id.Restaurant)
            et.setText("Restaurant: $selectedItem4")
            val et2 = view.findViewById<EditText>(R.id.Attendee)

            et2.setText("Attendee: $selectedItem5")

            var events = getEventsForDate(selectedItem3)
            //et.gravity = Gravity.CENTER
            val builder = AlertDialog.Builder(requireContext())
                .setTitle("수정 혹은 삭제")
                .setView(view)
                .setNegativeButton("수정",
                    DialogInterface.OnClickListener { dialog, which ->
                        val index = events.indexOf(selectedItem2)
                        if (events.isNotEmpty()) {
                            val index = events.indexOf(selectedItem2)
                            if (index != -1) {

                                val newValue = selectedItem2.substring(0, 14) + et.text.toString() + "," + et2.text.toString()
                                events.set(index,newValue)
                                selectedItem2 = newValue

                                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
                                adapter.notifyDataSetChanged()
                                Toast.makeText(requireContext(),"$events,adapter이후",Toast.LENGTH_LONG).show()
//events 제대로 들어있음
                                val count = events.size

                                Toast.makeText(requireContext(),"$count",Toast.LENGTH_SHORT).show()
                            } else {
                                // 해당 요소가 리스트에 없는 경우에 대한 처리
                                Toast.makeText(requireContext(), "해당 요소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // 리스트가 비어있는 경우에 대한 처리
                            val newValue = selectedItem2.substring(0, 14) + et.text.toString() + "," + et2.text.toString()
                            events.add(newValue)
                            selectedItem2=newValue
                            Toast.makeText(requireContext(),"$events,events",Toast.LENGTH_LONG).show()

                            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
                            eventListView.adapter=adapter

                            Toast.makeText(requireContext(), "리스트가 비어있습니다.", Toast.LENGTH_SHORT).show()
                            val count = events.size

                            Toast.makeText(requireContext(),"$count",Toast.LENGTH_SHORT).show()
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
        val restaurantNameEditText = dialogView.findViewById<EditText>(R.id.restaurantNameEditText)
        val attendeeNameEditText = dialogView.findViewById<EditText>(R.id.attendeeNameEditText)
        val datePickerButton = dialogView.findViewById<Button>(R.id.datePickerButton)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        datePickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    val restaurantName = restaurantNameEditText.text.toString()
                    val attendeeName = attendeeNameEditText.text.toString()

                    // Save the event (restaurant name and attendee name) to the selected date
                    saveEvent(selectedYear, selectedMonth, selectedDayOfMonth, restaurantName, attendeeName)

                    // Update the event list view
                    updateEventListView("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
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


