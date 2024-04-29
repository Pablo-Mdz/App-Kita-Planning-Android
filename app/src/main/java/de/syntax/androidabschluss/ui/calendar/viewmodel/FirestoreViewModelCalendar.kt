package de.syntax.androidabschluss.ui.calendar.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import de.syntax.androidabschluss.ui.events.data.model.Events

class FirestoreViewModelCalendar (application: Application) : AndroidViewModel(application) {


        private val db = FirebaseFirestore.getInstance()
        private val eventCollectionDB = db.collection("event")

        // LiveData for all events
        private val _eventsCalendar = MutableLiveData<QuerySnapshot>()
        val eventsCalendar: LiveData<QuerySnapshot>
            get() = _eventsCalendar

        init {
            eventCollectionDB.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    _eventsCalendar.value = snapshot
                } else {
                    Log.e("FirestoreViewModel", "Snapshot is null")
                }
            }
        }

        fun deleteEvent(eventId: String) {
            eventCollectionDB.document(eventId).delete()
        }
    }
