package de.syntax.androidabschluss.ui.daily

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.databinding.FragmentNewDailyInfoBinding
import de.syntax.androidabschluss.ui.daily.data.model.DailyInfo
import de.syntax.androidabschluss.ui.daily.viewmodel.DailyFirestoreViewModel


class NewDailyInfoFragment : Fragment() {

    private lateinit var binding: FragmentNewDailyInfoBinding
    private val viewModel: DailyFirestoreViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewDailyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val maxLength = 30
        val etTitle = binding.etDailyInfoTitleInput
        val tilTitle = binding.etDailyInfoTitle
        // Set the max length of the title input field
        etTitle.filters = arrayOf(InputFilter.LengthFilter(maxLength))
        tilTitle.setEndIconOnClickListener {
            val currentLength = etTitle.text?.length ?: 0
            if (currentLength >= maxLength) {
                Toast.makeText(requireContext(), "Max character limit reached", Toast.LENGTH_SHORT).show()
            }
        }
        val etDescription = binding.etDailyInfoDescriptionInput
        val tilDescription = binding.etDailyInfoDescription
        // Set the max length of the description input field
        etDescription.filters = arrayOf(InputFilter.LengthFilter(maxLength))
        tilDescription.setEndIconOnClickListener {
            val currentLength = etDescription.text?.length ?: 0
            if (currentLength >= maxLength) {
                Toast.makeText(requireContext(), "Max character limit reached", Toast.LENGTH_SHORT).show()
            }
        }

        // Save the daily info
        binding.btnSaveDailyInfo.setOnClickListener {
            val title = binding.etDailyInfoTitleInput.text.toString()
            val description = binding.etDailyInfoDescriptionInput.text.toString()
            val url = binding.etDailyInfoUrlInput.text.toString()

            val dailyInfo = DailyInfo(id = String(),title, description,url)
            viewModel.addDailyInfo(dailyInfo)
            findNavController().navigateUp()
        }
        binding.btnCancelDailyInfo.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}