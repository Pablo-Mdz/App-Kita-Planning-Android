package de.syntax.androidabschluss.ui.daily.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.databinding.ItemDailyInfoBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel
import de.syntax.androidabschluss.ui.daily.data.model.DailyInfo
import de.syntax.androidabschluss.ui.daily.viewmodel.DailyFirestoreViewModel
import de.syntax.androidabschluss.ui.events.EditEventFragmentDirections

class DailyAdapter(
    private val context: Context,
    private val viewModel: DailyFirestoreViewModel,
    private val firebaseViewModel: FirebaseViewModel,
    private val dataset: MutableList<DailyInfo>,
    ) : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

        inner class DailyViewHolder(val binding: ItemDailyInfoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding = ItemDailyInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return DailyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        var item = dataset[position]

        // set the cards "useful info for parents"
        holder.binding.tvTitleDaily.text = item.title
        holder.binding.tvDescriptionDaily.text = item.description
        holder.binding.buttonOpenUrl.setOnClickListener {
            val url = item.url
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                holder.itemView.context.startActivity(intent)
            } else {
                Toast.makeText(holder.itemView.context, "URL is empty", Toast.LENGTH_SHORT).show()
            }
        }
        holder.binding.ivDelete.visibility = if (firebaseViewModel.isTeacherLoggedIn()) View.VISIBLE else View.GONE
        // delete the event
        holder.binding.ivDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteInfo(item.id)
                    Toast.makeText(context, "Event deleted", Toast.LENGTH_SHORT).show()
                    deleteItem(position)
                }
                .setNegativeButton("No", null)
                .show()
        }

    }

    private fun deleteItem(position: Int) {
        dataset.removeAt(position)
        notifyItemRemoved(position)
    }
}