package de.syntax.androidabschluss.ui.events

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.MainActivity
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentNewEventBinding
import de.syntax.androidabschluss.ui.events.data.model.Events
import de.syntax.androidabschluss.ui.events.viewmodel.FirestoreViewModel
import java.util.UUID

class NewEventFragment : Fragment() {

    private lateinit var binding: FragmentNewEventBinding
    private val viewModel: FirestoreViewModel by activityViewModels()
    private var imageUrl = ""
    // permission for notification
    private val PERMISSION_REQUEST_CODE = 1001

    //set the image in the miniature
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.addIvContact.setImageURI(uri)
                uploadImage(uri)
            } else {
                Log.e("NewEventFragment", "Error picking image")
            }
        }
    companion object {
        const val MY_CHANNEL_ID = "myChannel"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSaveEvent.setOnClickListener {
            val title = binding.addTextInputName.text.toString()
            val description = binding.etDescription.text.toString()
            val date = binding.addDate.text.toString()
            val hour = binding.addHour.text.toString()
            val location = binding.addLocation.text.toString()

            if (imageUrl.isNotEmpty()) {
            val event = Events(title, description, date, hour, location, imageUrl)
            Log.d("NewEventFragment", "Image adresse: $imageUrl")
            viewModel.addEvent(event, imageUrl)
            } else {
                Log.e("NewEventFragment", "Error picking image in function onViewCreated()")
            }
            createChannel()
            createSimpleNotification()
            findNavController().navigate(NewEventFragmentDirections.actionNewEventFragmentToNavigationEvents())
        }
        // search for the image in the gallery
        binding.addImageButton.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
        binding.addDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.addHour.setOnClickListener {
            showTimePickerDialog()
        }
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(NewEventFragmentDirections.actionNewEventFragmentToNavigationEvents())
        }
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { time -> onTimeSelected(time) }
        timePicker.show(requireActivity().supportFragmentManager, "timePicker")
    }
    private fun onTimeSelected(time: String) {
        binding.addHour.setText(time)
    }
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDaySelected(day, month, year) }
        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }
    private fun onDaySelected(day: Int, month: Int, year: Int) {
        val formattedMonth = month + 1
        binding.addDate.setText("$day.$formattedMonth.$year")
    }
    private fun uploadImage(uri: Uri) {
        val storageReference = Firebase.storage.reference
        Log.d("NewEventFragment", " Storage Reference: $storageReference")
        val imageRef = storageReference.child("image/${UUID.randomUUID()}")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            Log.d("NewEventFragment", "Image upload successful")
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                imageUrl = downloadUri.toString()
                Log.d("NewEventFragment", "Image uploaded successfully. URL Img: $imageUrl")
            }.addOnFailureListener { exception ->
                Log.e("NewEventFragment", "Error getting download URL", exception)
            }
        }.addOnFailureListener { exception ->
            Log.e("NewEventFragment", "Error uploading image", exception)
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "MySuperChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Kita Abenteuer Notification Channel"
            }
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createSimpleNotification() {
        val intent = Intent(requireActivity(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            action = "OPEN_EVENTS_FRAGMENT" // Acción personalizada para abrir el fragmento de eventos
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(requireActivity(), MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_new)
            .setContentTitle("New Event In Kita Abenteuer")
            .setContentText("A new event has been created in the kita, check it out!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireActivity())) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                notify(1, builder.build())
            }
        }
    }


}
