package de.syntax.androidabschluss.ui.events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentEventsBinding
import de.syntax.androidabschluss.ui.events.adapter.EventsAdapter
import de.syntax.androidabschluss.ui.events.data.model.Events
import de.syntax.androidabschluss.ui.events.viewmodel.FirestoreViewModel
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel


class EventsFragment : Fragment() {


    private lateinit var binding: FragmentEventsBinding
    private val viewModel: FirestoreViewModel by activityViewModels()
    private lateinit var userMail: String
    // to get the current user from DB
    private val firebaseViewModel: FirebaseViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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


        // sidebar menu
        binding.apply {
            toolbar.setNavigationOnClickListener {
                drawerLayout.open()
            }
            sidebarMenu.setNavigationItemSelectedListener { menuItem ->
                menuItem.isChecked = true
                when (menuItem.itemId) {
                    R.id.item_home -> {
                        findNavController().navigate(EventsFragmentDirections.actionNavigationEventsToNavigationHome())
                    }
                    R.id.item_bot_ia -> {
                        findNavController().navigate(EventsFragmentDirections.actionNavigationEventsToIAChatFragment())
                    }
                    R.id.add_new_user  -> {
                        findNavController().navigate(EventsFragmentDirections.actionNavigationEventsToSignUpFragment2())
                    }
                    R.id.item_logout -> {
                        firebaseViewModel.logout()
                        findNavController().navigate(EventsFragmentDirections.actionNavigationEventsToWelcomeActivity())
                    }
                    R.id.item_info -> {
                        findNavController().navigate(EventsFragmentDirections.actionNavigationEventsToInfoFragment())
                    }
                }
                return@setNavigationItemSelectedListener true

            }
        }

        //search bar
        binding.searchBar.editText?.addTextChangedListener { text ->
            viewModel.searchFun(text.toString()).observe(viewLifecycleOwner) { events ->
                binding.recyclerViewEvents.adapter = EventsAdapter(events, firebaseViewModel)
            }
        }

        //toolbar
        firebaseViewModel.currentTeacher.observe(viewLifecycleOwner) { teacher ->
            if (teacher != null) {
                binding.toolbar.inflateMenu(R.menu.help_menu)
            }
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_add_event -> {
                    findNavController().navigate(EventsFragmentDirections.actionNavigationEventsToNewEventFragment())
                    true
                }
                else -> false
            }
        }

        // to show the list of events (recyclerview)
        viewModel.events.observe(viewLifecycleOwner) { snapShot ->
            if (snapShot != null) {
                val eventList = mutableListOf<Events>()
                for (document in snapShot.documents) {
                    val event = document.toObject(Events::class.java)
                    if (event != null) {
                        event.id = document.id
                        eventList.add(event)
                    }
                }
                Log.e("EventsFragment", "Retrieved: ${eventList.size} events")
                binding.recyclerViewEvents.adapter = EventsAdapter(eventList, firebaseViewModel)
            } else {
                Log.e("EventsFragment", "No events found")
            }
        }
    }
}

