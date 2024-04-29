package de.syntax.androidabschluss.ui.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.syntax.androidabschluss.databinding.ItemCalendarEventBinding
import de.syntax.androidabschluss.ui.calendar.viewmodel.FirestoreViewModelCalendar
import de.syntax.androidabschluss.ui.events.data.model.Events


class CalendarAdapter(
    private var dataset: MutableList<Events>,
    private val viewModelCalendar: FirestoreViewModelCalendar,
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private val hiddenItems = mutableListOf<Int>()

    inner class CalendarViewHolder(val binding: ItemCalendarEventBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            ItemCalendarEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        var item = dataset[position]

        holder.binding.tvTitleCalendar.text = item.title
        holder.binding.tvDateCalendar.text = item.date
        holder.binding.tvHourCalendar.text = item.hour

        // Hide item from recyclerview
        if (hiddenItems.contains(position)) {
            holder.itemView.visibility = View.GONE
        } else {
            holder.itemView.visibility = View.VISIBLE
        }
    }

    // get item from dataset
    fun getItem(position: Int): Events {
        return dataset[position]
    }

    fun removeItem(position: Int) {
        dataset.removeAt(position)
        notifyDataSetChanged()
        viewModelCalendar.deleteEvent(getItem(position).id)

    }
    fun setEvents(events: List<Events>) {
        dataset = events.toMutableList()
    }

}