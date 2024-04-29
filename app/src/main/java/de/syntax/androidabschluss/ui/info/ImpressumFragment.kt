package de.syntax.androidabschluss.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentImpressumBinding
import de.syntax.androidabschluss.databinding.FragmentInfoBinding

class ImpressumFragment : Fragment() {


    private lateinit var binding: FragmentImpressumBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImpressumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}