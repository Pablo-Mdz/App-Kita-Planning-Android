package de.syntax.androidabschluss.ui.calendar

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.databinding.FragmentCalendarBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.ui.calendar.adapter.CalendarAdapter
import de.syntax.androidabschluss.ui.calendar.viewmodel.FirestoreViewModelCalendar
import de.syntax.androidabschluss.ui.events.data.model.Events
import java.util.Locale


class CalendarFragment : Fragment() {

    private val viewModel: FirestoreViewModelCalendar by activityViewModels()
    private val firebaseViewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var userMail: String
    private val eventList = mutableListOf<Events>()
    private lateinit var calendarAdapter: CalendarAdapter



    inner class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        // swipe to delete
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val savedAdapter = binding.rvCalendarEvents.adapter as? CalendarAdapter?

            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("Delete Event")
                setMessage("Are you sure you want to delete this event?")
                setPositiveButton("Yes") { dialog, _ ->
                    val deletedItem = savedAdapter?.getItem(position)
                    deletedItem?.let { viewModel.deleteEvent(it.id) }
                    savedAdapter?.removeItem(position)
                    dialog.dismiss() }
                setNegativeButton("No") { dialog, _ ->
                    savedAdapter?.notifyItemChanged(position)
                    dialog.dismiss() }
                create().show() }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarAdapter = CalendarAdapter(
            mutableListOf(),
            viewModel
        )
        binding.rvCalendarEvents.adapter = calendarAdapter

       // recyclerView where show the events
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            // format the date, important! (to match with the date in the database)
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val selectedDate = formatter.format(selectedCalendar.time)
            val filteredEvents = filterByDate(eventList, selectedDate)

            if (filteredEvents.isNotEmpty()) {
                calendarAdapter.setEvents(filteredEvents)
                calendarAdapter.notifyDataSetChanged()
            } else {
                calendarAdapter.setEvents(emptyList())
                calendarAdapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "No events for this day", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        //signin menu
        val addNewUserItem = binding.sidebarMenu.menu.findItem(R.id.add_new_user)
        //side menu signup - welcome user
        firebaseViewModel.currentTeacher.observe(viewLifecycleOwner) { teacher ->
            addNewUserItem.isVisible = teacher != null
            val welcomeText = binding.sidebarMenu.getHeaderView(0)
                .findViewById<TextView>(R.id.username)
            if (teacher != null) {
                val userName = teacher.displayName ?: teacher.email
                Log.e("HomeFragment", "Teacher: $userName")
                welcomeText.text = "Welcome Teacher $userName"
            }
        }

        firebaseViewModel.currentParent.observe(viewLifecycleOwner) { parent ->
            val welcomeText = binding.sidebarMenu.getHeaderView(0)
                .findViewById<TextView>(R.id.username)
            if (parent != null && firebaseViewModel.currentTeacher.value == null) {
                val userName = parent.displayName ?: parent.email
                welcomeText.text = "Welcome Parent $userName"
            }
        }
        // to see the cards in the reciclerview
        viewModel.eventsCalendar.observe(viewLifecycleOwner) { snapShot ->
            if (snapShot != null) {
                eventList.clear()
                for (document in snapShot.documents) {
                    val event = document.toObject(Events::class.java)
                    if (event != null) {
                        event.id = document.id
                        eventList.add(event)
                    }
                }
                eventList.sortBy { it.date }
                calendarAdapter.setEvents(eventList)
                calendarAdapter.notifyDataSetChanged()

            } else {
                Log.e("EventsFragment", "No events found")
            }
        }

        // checkbox to show all events
        binding.checkboxShowAllEvents.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                calendarAdapter.setEvents(eventList)
            } else {
                calendarAdapter.setEvents(emptyList())
            }
            calendarAdapter.notifyDataSetChanged()
        }
        if (eventList.isNotEmpty() && binding.rvCalendarEvents.adapter != null) {
            binding.rvCalendarEvents.adapter?.notifyDataSetChanged()
        } else {
            calendarAdapter.setEvents(emptyList())
            Toast.makeText(requireContext(), "No event this day", Toast.LENGTH_SHORT).show()
        }
        // sidebar menu
        binding.apply {
            toolbar.setNavigationOnClickListener {
                drawerLayout.open()
            }
            sidebarMenu.setNavigationItemSelectedListener { menuItem ->
                menuItem.isChecked = true
                when (menuItem.itemId) {
                    R.id.item_home -> {
                        findNavController().navigate(R.id.navigation_home)
                    }
                    R.id.item_bot_ia -> {
                        findNavController().navigate(CalendarFragmentDirections.actionNavigationCalendarToIAChatFragment())
                    }

                    R.id.add_new_user -> {
                        findNavController().navigate(CalendarFragmentDirections.actionNavigationCalendarToSignUpFragment2())
                    }

                    R.id.item_logout -> {
                        firebaseViewModel.logout()
                        findNavController().navigate(CalendarFragmentDirections.actionNavigationSettingsToWelcomeActivity())
                    }

                    R.id.item_info -> {
                        findNavController().navigate(CalendarFragmentDirections.actionNavigationCalendarToInfoFragment())
                    }

                }
                return@setNavigationItemSelectedListener true

            }
        }

        // swipe to delete
        firebaseViewModel.currentTeacher.observe(viewLifecycleOwner) { teacher ->
            if (teacher != null) {
        val swipeToDeleteCallback = SwipeToDeleteCallback()
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvCalendarEvents)
        }
        }

    }

    // filter the events by date, Very important!
    private fun filterByDate(events: List<Events>, date: String): List<Events> {
        val result = mutableListOf<Events>()

        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(date)
        // format the date
        val monthNumber = calendar.get(Calendar.MONTH) + 1
        val month = String.format("%02d", monthNumber)
        val formattedSelectedDate =
            "${calendar.get(Calendar.DAY_OF_MONTH)}.$month.${calendar.get(Calendar.YEAR)}"

        for (event in events) {
            val eventDateParts = event.date.split(".")
            val eventDay = eventDateParts[0]
            val eventMonth = eventDateParts[1]
            val eventYear = eventDateParts[2]

            val formattedEventMonth = String.format("%02d", eventMonth.toInt())
            val eventDateFormatted = "$eventDay.$formattedEventMonth.$eventYear"

            if (eventDateFormatted == formattedSelectedDate) {
                result.add(event)
            }
        }
            Log.d("CalendarFragment", "Result: $result")
        return result
    }
}