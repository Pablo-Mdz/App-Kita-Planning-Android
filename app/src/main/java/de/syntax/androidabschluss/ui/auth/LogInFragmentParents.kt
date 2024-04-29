package de.syntax.androidabschluss.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentLogInParentsBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel


class LogInFragmentParents : Fragment() {

    private val viewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentLogInParentsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInParentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    // Login with email and password
        binding.btnLogin.setOnClickListener {
            val email = binding.addTextInputName.text.toString()
            val password = binding.password.text.toString()
         if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
             viewModel.login(email, password)
         } else{
             Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
         }
        }

        binding.arrowLeft.setOnClickListener {
            findNavController().navigateUp()
        }

        // Navigate to MainActivity if Parent is logged in
        viewModel.currentParent.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(LogInFragmentParentsDirections.actionLogInFragmentParentsToMainActivity2())
            }
        }

        // Copy email to clipboard
        binding.icCopyMail.setOnClickListener {
            val textToCopy = "test@parents.com"
            val clipboardManager =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
        // Copy password to clipboard
        binding.icCopyPassword.setOnClickListener {

            val textToCopy = "123456"
            val clipboardManager =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        // Animate horse and logo
        moveLogo()
        animateHorse(binding.gifHorse)
    }

    private fun animateHorse(horse: ImageView) {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels.toFloat()
        val screenHeight = displayMetrics.heightPixels.toFloat()
        val duration = (screenWidth / 100) * 700

        // horizontal movement
        val moveHorseX = ObjectAnimator.ofFloat(horse, View.TRANSLATION_X, -horse.width.toFloat(), screenWidth)
        moveHorseX.duration = duration.toLong()
        moveHorseX.interpolator = LinearInterpolator()
        moveHorseX.repeatCount = ObjectAnimator.INFINITE

        // jump
        val moveHorseY = ObjectAnimator.ofFloat(horse, View.TRANSLATION_Y, 0f, -screenHeight / 25, 0f)
        moveHorseY.duration = 700
        moveHorseY.interpolator = AccelerateDecelerateInterpolator()
        moveHorseY.repeatCount = ObjectAnimator.INFINITE

        val horseAnimatorSet = AnimatorSet()
        horseAnimatorSet.playTogether(moveHorseX, moveHorseY)
        horseAnimatorSet.start()
    }

    private fun moveLogo() {
        val rotationAnimator = ObjectAnimator.ofFloat(binding.logo, View.ROTATION, -5f, 5f)
        rotationAnimator.duration = 2500
        rotationAnimator.repeatCount = ObjectAnimator.INFINITE
        rotationAnimator.repeatMode = ObjectAnimator.REVERSE
        rotationAnimator.interpolator = AccelerateDecelerateInterpolator()
        rotationAnimator.start()
    }
}