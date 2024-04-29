package de.syntax.androidabschluss.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentDataProtectionBinding
import de.syntax.androidabschluss.databinding.FragmentOpenSourceLicensesBinding


class OpenSourceLicensesFragment : Fragment() {
    private lateinit var binding: FragmentOpenSourceLicensesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOpenSourceLicensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}