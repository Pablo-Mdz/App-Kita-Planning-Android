package de.syntax.androidabschluss.ui.daily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.databinding.ItemListDailyBinding
import de.syntax.androidabschluss.ui.daily.data.model.Notes
import de.syntax.androidabschluss.ui.daily.viewmodel.DailyFirestoreViewModel

class NotesAdapter(
    private val dataset: MutableList<Notes>,
    private val viewModelNotes: DailyFirestoreViewModel,
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(val binding: ItemListDailyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            ItemListDailyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        var item = dataset[position]

        //show the notes
        holder.binding.tvText.text = item.text
    }

    fun getItem(position: Int): Notes {
        return dataset[position]
    }

    fun removeItem(position: Int) {
        if (position in 0 until dataset.size) {
            val removedNote = dataset[position]
            dataset.removeAt(position)
            notifyDataSetChanged()
            viewModelNotes.deleteNotes(removedNote.id)
        }
    }
}
