package de.syntax.androidabschluss.ui.aboutUs

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentAboutUsBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel

class AboutUsFragment : Fragment(), OnMapReadyCallback {

    private val firebaseViewModel: FirebaseViewModel by activityViewModels()
    private lateinit var userMail: String
    private lateinit var binding: FragmentAboutUsBinding
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createFragment()


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
                        findNavController().navigate(AboutUsFragmentDirections.actionNavigationAboutUsToIAChatFragment())
                    }

                    R.id.add_new_user -> {
                        findNavController().navigate(AboutUsFragmentDirections.actionNavigationAboutUsToSignUpFragment2())
                    }

                    R.id.item_logout -> {
                        firebaseViewModel.logout()
                        findNavController().navigate(AboutUsFragmentDirections.actionNavigationAboutUsToWelcomeActivity())
                    }

                    R.id.item_info -> {
                        findNavController().navigate(AboutUsFragmentDirections.actionNavigationAboutUsToInfoFragment())
                    }
                }
                return@setNavigationItemSelectedListener true

            }
        }

        animateButton(binding.btnSend)
        binding.btnSend.setOnClickListener {
            sendEMail()
        }
    }

    private fun sendEMail() {
        val recipientEmail = "pablocigoy@gmail.com"
        val senderEmail = binding.email.text.toString()
        val subject = binding.email.text.toString()
        val message = binding.message.text.toString()

        if (senderEmail.isEmpty() || subject.isEmpty() || message.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(
                Intent.EXTRA_TEXT,
                "Email sent from Abenteuer app from the E-mail: $senderEmail\n\n$message"
            )
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Send Email"))
        } else {
            Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    private fun createMarker() {
        val coordinates = LatLng(52.5550422, 13.4076049)
        val marker = MarkerOptions().position(coordinates).title("Kita Aventeuer")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 15f),
            1000,
            null
        )
    }

    private fun animateButton(btn: ImageView) {

        val moveRight = ObjectAnimator.ofFloat(btn, View.TRANSLATION_X, 0f, 50f)
        moveRight.duration = 1500
        moveRight.repeatCount = ObjectAnimator.INFINITE
        moveRight.repeatMode = ObjectAnimator.REVERSE
        moveRight.start()
    }

}