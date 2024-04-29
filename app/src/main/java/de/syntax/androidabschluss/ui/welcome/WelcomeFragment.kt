package de.syntax.androidabschluss.ui.welcome

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentWelcomeBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private val firebaseViewModel: FirebaseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //btn for parents
        binding.cvParents.setOnClickListener {
            firebaseViewModel.currentParent.observe(viewLifecycleOwner) { parent ->
                if (parent != null && parent?.email?.endsWith("@parents.com") == true) {
                    findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToMainActivity2())
                } else if (parent != null) {
                    Toast.makeText(
                        context,
                        "you are not a teacher, please enter as parent",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragmentParents())
                }
            }
        }

        //btn for teachers
        binding.cvTeachers.setOnClickListener {
            firebaseViewModel.currentTeacher.observe(viewLifecycleOwner) { teacher ->
                if (teacher != null && teacher?.email?.endsWith("@abenteuer.com") == true) {
                    findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToMainActivity2())
                } else if (teacher != null && teacher?.email?.endsWith("@abenteuer.com") == false) {
                    Toast.makeText(
                        context,
                        "you are not a parent, please enter as Teacher",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (teacher == null) {
                    findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToLogInFragmentTeachers2())
                }

            }
        }

        // navigate btn to social media
        binding.ivGithub.setOnClickListener {
            val url = "https://github.com/Pablo-Mdz"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        binding.ivLinkedin.setOnClickListener {
            val url = "https://www.linkedin.com/in/pablo-cigoy/?locale=en_US"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}