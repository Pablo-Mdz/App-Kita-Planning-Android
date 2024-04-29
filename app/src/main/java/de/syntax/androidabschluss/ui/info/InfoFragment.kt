package de.syntax.androidabschluss.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {


    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //show menu and navigate to the selected fragment
        binding.tvImpressum.setOnClickListener {
            val action = InfoFragmentDirections.actionInfoFragmentToImpressumFragment()
            findNavController().navigate(action)
        }

        binding.tvDataProtection.setOnClickListener {
            val action = InfoFragmentDirections.actionInfoFragmentToDataProtectionFragment()
            findNavController().navigate(action)
        }

        binding.tvThermsAndConditions.setOnClickListener {
            val action = InfoFragmentDirections.actionInfoFragmentToThermsAndConditionsFragment()
            findNavController().navigate(action)
        }
        binding.tvOpenSourceLicenses.setOnClickListener {
            val action = InfoFragmentDirections.actionInfoFragmentToOpenSourceLicensesFragment()
            findNavController().navigate(action)
        }

        binding.toolbar.setNavigationOnClickListener {
            val action = InfoFragmentDirections.actionInfoFragmentToNavigationHome()
            findNavController().navigate(action)
        }
    }
    }