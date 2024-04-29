package de.syntax.androidabschluss.ui.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import de.syntax.androidabschluss.databinding.FragmentAddNoteBinding
import de.syntax.androidabschluss.ui.daily.data.model.Notes
import de.syntax.androidabschluss.ui.daily.viewmodel.DailyFirestoreViewModel
import java.util.UUID


class AddNoteFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private val viewModelNotes: DailyFirestoreViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Save note in DB
        binding.btnSaveNote.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val title = binding.etNote.text.toString()
            if (title.isNotEmpty()) {
                val note = Notes(id,title)
                viewModelNotes.addNotes(note)
                findNavController().navigateUp()
            } else {
                binding.etNote.error = "Please enter a note"
            }
        }
    }


}