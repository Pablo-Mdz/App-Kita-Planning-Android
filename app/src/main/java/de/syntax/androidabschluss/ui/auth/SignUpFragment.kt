package de.syntax.androidabschluss.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentSignUpBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel

class SignUpFragment : Fragment() {

    private val viewModel: FirebaseViewModel by activityViewModels()
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sign up button
        binding.BtnSignUp.setOnClickListener {
            val email = binding.tvMail.text.toString()
            val password = binding.password.text.toString()
            val name = binding.username.text.toString()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !name.isNullOrEmpty()) {
            viewModel.signup(email,password, name)
            Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.navigation_home)
            }
        }


        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }


    }



}