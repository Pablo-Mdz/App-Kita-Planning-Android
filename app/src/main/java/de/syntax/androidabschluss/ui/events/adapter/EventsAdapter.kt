package de.syntax.androidabschluss.ui.events.adapter


import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.databinding.ItemCardEventBinding
import de.syntax.androidabschluss.ui.auth.viewModel.FirebaseViewModel
import de.syntax.androidabschluss.ui.events.EventsFragmentDirections
import de.syntax.androidabschluss.ui.events.data.model.Events

class EventsAdapter (
    private val dataset: List<Events>,
    private val firebaseViewModel: FirebaseViewModel,
) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    inner class EventsViewHolder(val binding: ItemCardEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val binding = ItemCardEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        var item = dataset[position]


        holder.binding.tvEventTitle.text = item.title
        holder.binding.tvDescription.text = item.description
        holder.binding.tvDate.text = item.date
        holder.binding.tvHour.text = item.hour
        holder.binding.tvPlace.text = item.location
        holder.binding.ivEventImage.load(item.image) {
            error(R.drawable.child1)
        }
        // visibility of edit btn only for teachers
        holder.binding.btnEdit.visibility = if (firebaseViewModel.isTeacherLoggedIn()) View.VISIBLE else View.GONE
        holder.binding.btnEdit.setOnClickListener { view ->
            view.findNavController().navigate(EventsFragmentDirections.actionNavigationEventsToEditEventFragment(eventId = item.id))
        }
        //collapse btn to see description
        holder.binding.btnSeeMore.setOnClickListener {
            if (holder.binding.expandableLayout.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.binding.itemCardEvent, AutoTransition())
                holder.binding.expandableLayout.visibility = View.VISIBLE
                holder.binding.btnSeeMore.text = "See Less"

            } else {
                TransitionManager.beginDelayedTransition(holder.binding.itemCardEvent, AutoTransition())
                holder.binding.expandableLayout.visibility = View.GONE
                holder.binding.btnSeeMore.text = "See More"
            }
        }



    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
