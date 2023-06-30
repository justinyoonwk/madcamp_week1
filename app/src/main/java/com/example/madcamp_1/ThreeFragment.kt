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

        // Find the necessary views
        calendarView = view.findViewById(R.id.calendarView)
        eventListView = view.findViewById(R.id.eventListView)
        val addEventButton = view.findViewById<Button>(R.id.addEventButton)

        // Set the date click listener for the calendar view
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"

            // Retrieve existing events for the selected date and display them in the event list view
            val events = getEventsForDate(selectedDate)
            if (events.isEmpty()) {
                eventListView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayOf("No events"))
            } else {
                eventListView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, events)
            }
        }

        // Set the click listener for the add event button
        addEventButton.setOnClickListener {
            showAddEventDialog()
        }

        return view
    }

    private fun getEventsForDate(date: String): List<String> {
        return eventList[date] ?: emptyList()
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
