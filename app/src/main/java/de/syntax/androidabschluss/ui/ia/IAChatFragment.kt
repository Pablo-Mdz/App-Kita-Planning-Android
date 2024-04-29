package de.syntax.androidabschluss.ui.ia

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.FragmentIAChatBinding
import de.syntax.androidabschluss.ui.ia.models.data.Message
import de.syntax.androidabschluss.ui.ia.utils.hideKeyBoard
import de.syntax.androidabschluss.ui.ia.utils.longToastShow
import de.syntax.androidabschluss.ui.ia.viewmodel.IaViewModel

class IAChatFragment : Fragment() {
        private lateinit var binding: FragmentIAChatBinding
        private val viewModel: IaViewModel by lazy {
            ViewModelProvider(this)[IaViewModel::class.java]
        }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentIAChatBinding.inflate(inflater, container, false)
            return binding.root
        }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        // get response from the GPT-3.5 model
            binding.sendButton.setOnClickListener {
                view.context.hideKeyBoard(it)
                val messageText = binding.messageInput.text.toString().trim()
                if (messageText.isNotEmpty()) {
                    val message = Message(content = messageText, role = "user")
                    // use the model name "gpt-3.5-turbo-0613" to get a response from the GPT-3.5 model
                    viewModel.createChatCompletion(listOf(message), "gpt-3.5-turbo-0613")
                    binding.messageInput.text = null
                } else {
                    view.context.longToastShow("Make a question!")
                }
            }
            // show the progress bar while the response is loading
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.responseText.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
            // show the response from the GPT-3.5 model
            viewModel.chatResponse.observe(viewLifecycleOwner) { response ->
                response?.let {
                    binding.responseText.text = it
                }
            }
            viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
                error?.let {
                    view.context.longToastShow(it)
                }
            }
            // back to the previous fragment
            binding.toolbar.setOnClickListener{
                findNavController().navigateUp()
            }
            // copy the response to the clipboard
            binding.icCopy.setOnClickListener {
                val textToCopy = binding.responseText.text.toString()
                val clipboardManager =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Text", textToCopy)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                binding.icCopy.setBackgroundResource(R.drawable.ic_copy)
                android.os.Handler().postDelayed({
                    binding.icCopy.setBackgroundResource(R.drawable.ic_copy)
                }, 1000)
            }
        }
    }