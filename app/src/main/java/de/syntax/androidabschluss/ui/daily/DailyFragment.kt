package de.syntax.androidabschluss.ui.daily

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentDailyBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel
import de.syntax.androidabschluss.ui.daily.adapter.DailyAdapter
import de.syntax.androidabschluss.ui.daily.adapter.NotesAdapter
import de.syntax.androidabschluss.ui.daily.data.model.DailyInfo
import de.syntax.androidabschluss.ui.daily.data.model.Notes
import de.syntax.androidabschluss.ui.daily.viewmodel.DailyFirestoreViewModel

const val TAG = "DailyFragment"

class DailyFragment : Fragment() {


    private lateinit var binding: FragmentDailyBinding
    private val userViewModel: FirebaseViewModel by activityViewModels()
    private val fireStoreViewModel: DailyFirestoreViewModel by activityViewModels()
    private lateinit var userMail: String


    inner class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        // Swipe to delete
        override fun onMove(
            recyclerView: RecyclerView,
            fireStoreViewModel: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean { return false }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val adapter = binding.rvListNotes.adapter as NotesAdapter?
            val deletedNote = adapter?.getItem(position)
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Delete Note")
                setMessage("Are you sure you want to delete this note?")
                setPositiveButton("Yes") { dialog, _ ->
                    adapter?.removeItem(position)
                    deletedNote?.let { fireStoreViewModel.deleteNotes(it.id) }
                    dialog.dismiss()
                }
                setNegativeButton("No") { dialog, _ ->
                    adapter?.notifyItemChanged(position)
                    dialog.dismiss()
                }
                create().show()
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //signin menu
        val addNewUserItem = binding.sidebarMenu.menu.findItem(R.id.add_new_user)
        //side menu signup - welcome user
        userViewModel.currentTeacher.observe(viewLifecycleOwner) { teacher ->
            addNewUserItem.isVisible = teacher != null
            val welcomeText = binding.sidebarMenu.getHeaderView(0)
                .findViewById<TextView>(R.id.username)
            if (teacher != null) {
                val userName = teacher.displayName ?: teacher.email
                Log.e("HomeFragment", "Teacher: $userName")
                welcomeText.text = "Welcome Teacher $userName"
            }
        }

        userViewModel.currentParent.observe(viewLifecycleOwner) { parent ->
            val welcomeText = binding.sidebarMenu.getHeaderView(0)
                .findViewById<TextView>(R.id.username)
            if (parent != null && userViewModel.currentTeacher.value == null) {
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
                        findNavController().navigate(DailyFragmentDirections.actionNavigationDailyToNavigationHome())
                    }
                    R.id.item_bot_ia -> {
                        findNavController().navigate(DailyFragmentDirections.actionNavigationDailyToIAChatFragment())
                    }


                    R.id.add_new_user -> {
                        findNavController().navigate(DailyFragmentDirections.actionNavigationDailyToSignUpFragment2())
                    }

                    R.id.item_logout -> {
                        userViewModel.logout()
                        findNavController().navigate(DailyFragmentDirections.actionNavigationDailyToWelcomeActivity())
                    }

                    R.id.item_info -> {
                        findNavController().navigate(DailyFragmentDirections.actionNavigationDailyToInfoFragment())
                    }
                }
                return@setNavigationItemSelectedListener true

            }
        }
// get list of needs from firestore
        fireStoreViewModel.notes.observe(viewLifecycleOwner) { snapshot ->
            if (snapshot != null) {
                val notesList = mutableListOf<Notes>()

                for (document in snapshot.documents) {
                    val id = document.id
                    val text = document.getString("text") ?: ""
                    val notesCard = Notes(id, text)
                    notesList.add(notesCard)
                }
                Log.e(TAG, "Retrieved: ${notesList.size} notes")
                val sortedNotesList = notesList.sortedBy { it.id }.toMutableList()
                binding.rvListNotes.adapter = NotesAdapter(sortedNotesList, fireStoreViewModel)
            } else {
                Log.e(TAG, "No notes found")
            }
        }

    // get list of useful info for parents from firestore
        fireStoreViewModel.cardInfo.observe(viewLifecycleOwner) { snapShot ->
            if (snapShot != null) {
                val infoList = mutableListOf<DailyInfo>()
                for (document in snapShot.documents) {
                    val infoCard = document.toObject(DailyInfo::class.java)
                    if (infoCard != null) {
                        infoCard.id = document.id
                        infoList.add(infoCard)
                    }
                }
                Log.e(TAG, "Retrieved: ${infoList.size} events")
                binding.rvDailyInfo.adapter =
                    DailyAdapter(requireContext(), fireStoreViewModel, userViewModel, infoList)
            } else {
                Log.e(TAG, "No info Card found")
            }
        }


        // show add button only if teacher is logged in
        userViewModel.currentTeacher.observe(viewLifecycleOwner) { teacher ->
            binding.ivAddDailyInfo.isVisible = teacher != null
            binding.ivAddNote.isVisible = teacher != null
        }
        binding.ivAddDailyInfo.setOnClickListener {
            findNavController().navigate(DailyFragmentDirections.actionNavigationDailyToNewDailyInfoFragment())
        }

        binding.ivAddNote.setOnClickListener {
            findNavController().navigate(DailyFragmentDirections.actionNavigationDailyToAddNoteFragment())
        }
        // swipe to delete only for teachers
        userViewModel.currentTeacher.observe(viewLifecycleOwner) { teacher ->
            if (teacher != null) {
                val swipeToDeleteCallback = SwipeToDeleteCallback()
                val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvListNotes)
            }
        }
    }
}