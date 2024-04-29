package de.syntax.androidabschluss.ui.events

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.Coil
import coil.load
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentEditEventBinding
import de.syntax.androidabschluss.ui.events.data.model.Events
import de.syntax.androidabschluss.ui.events.viewmodel.FirestoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID


class EditEventFragment : Fragment() {

    private lateinit var binding: FragmentEditEventBinding
    private val viewModel: FirestoreViewModel by activityViewModels()
    private var eventId: String = ""
    private var imageUrl = ""
    // show the current picture of the event in miniature
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                binding.addIvContact.setImageURI(uri)
                uploadImage(uri)
            } else {
                Log.e("NewEventFragment", "Error picking image")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("eventId")?.let {
            eventId = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show the current event data in the edit fields
        eventId?.let {
            viewModel.getEventById(it).observe(viewLifecycleOwner) { event ->
                Log.d("EditEventFragment", "Event before id: $event")
                if (event != null) {
                    binding.addTextInputName.setText(event.title)
                    binding.etDescription.setText(event.description)
                    binding.addDate.setText(event.date)
                    binding.addHour.setText(event.hour)
                    binding.addLocation.setText(event.location)
                    binding.addIvContact.load(event.image)
                    Log.d("EditEventFragment", "Event with id: $event")
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        // save current event data
        binding.btnSaveEvent.setOnClickListener {
            val title = binding.addTextInputName.text.toString()
            val description = binding.etDescription.text.toString()
            val date = binding.addDate.text.toString()
            val location = binding.addLocation.text.toString()
            val hour = binding.addHour.text.toString()
            val image = binding.addIvContact.toString()
            val currentEvent = viewModel.getEventById(eventId).value
            if (imageUrl.isNotEmpty()) {
                Log.d("EditEventFragment", "Updating event with new image: $imageUrl")
                val event = Events(title, description, date, hour, location, imageUrl, eventId)
                viewModel.updateEvent(event)
                binding.addIvContact.invalidate()
                findNavController().navigate(EditEventFragmentDirections.actionEditEventFragmentToNavigationEvents())
            } else {
                if (currentEvent != null) {
                    val event = Events(
                        title,
                        description,
                        date,
                        hour,
                        location,
                        image,
                        eventId
                    )
                    Log.d("EditEventFragment", "Updating event without changing image: $event")
                    viewModel.updateEvent(event)
                    findNavController().navigate(EditEventFragmentDirections.actionEditEventFragmentToNavigationEvents())
                }
            }
        }

        binding.addDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.addHour.setOnClickListener {
            showTimePickerDialog()
        }
        binding.addImageButton.setOnClickListener {
            pickMedia.launch("image/*")
        }
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(EditEventFragmentDirections.actionEditEventFragmentToNavigationEvents())
        }
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteEvent(eventId)
                    Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(EditEventFragmentDirections.actionEditEventFragmentToNavigationEvents())
                }
                .setNegativeButton("No", null)
                .show()
        }

    }


    //picker time
    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { time -> onTimeSelected(time) }
        timePicker.show(requireActivity().supportFragmentManager, "timePicker")

    }

    private fun onTimeSelected(time: String) {
        binding.addHour.setText(time)
    }

    //picker date
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDaySelected(day, month, year) }
        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun onDaySelected(day: Int, month: Int, year: Int) {
        binding.addDate.setText("$day.$month.$year")
    }

    private fun uploadImage(uri: Uri) {
        val storageReference = Firebase.storage.reference
        val imageRef = storageReference.child("image/${UUID.randomUUID()}.jpg")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener { _ ->
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                imageUrl = downloadUri.toString()
                Log.d("EditEventFragment", "Image uploaded successfully. URL Img: $imageUrl")
                // update event with new image URL
                val updatedEvent = viewModel.getEventById(eventId).value
                updatedEvent?.let {
                    it.image = imageUrl
                    viewModel.updateEvent(it)
                }
            }.addOnFailureListener { exception ->
                Log.e("EditEventFragment", "Error getting download URL", exception)
            }
        }.addOnFailureListener { exception ->
            Log.e("EditEventFragment", "Error uploading image", exception)
        }
    }


}


