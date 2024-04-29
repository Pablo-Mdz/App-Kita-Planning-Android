package de.syntax.androidabschluss.ui.events.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import de.syntax.androidabschluss.ui.events.data.model.Events
import com.google.firebase.firestore.CollectionReference

class FirestoreViewModel(application: Application) : AndroidViewModel(application) {


    private val db = FirebaseFirestore.getInstance()
    private val eventCollectionDB = db.collection("event")

    private val _events = MutableLiveData<QuerySnapshot>()
    val events: LiveData<QuerySnapshot>
        get() = _events

    init {
        // Listen to changes in the Firestore collection "event"
        eventCollectionDB.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _events.value = snapshot
            } else {
                Log.e("FirestoreViewModel", "Snapshot is null")
            }
        }
    }

    fun addEvent(newEvent: Events, imageUrl: String) {
        val updatedEvent = newEvent.copy(image = imageUrl)
        eventCollectionDB.add(updatedEvent)
    }

    // search function
    fun searchFun(text: String): LiveData<List<Events>> {
        val result = MutableLiveData<List<Events>>()

        eventCollectionDB.get().addOnSuccessListener { documents ->
            val eventList = documents.mapNotNull { it.toObject(Events::class.java) }
            if (text.isNotEmpty()) {
                val filteredList = eventList.filter { it.title.contains(text, ignoreCase = true) }
                result.value = filteredList
            } else {
                result.value = eventList
            }
        }
        return result
    }

    fun deleteEvent(eventId: String) {
        eventCollectionDB.document(eventId).delete()
    }


    fun getEventById(eventId: String?): LiveData<Events?> {
        val eventLiveData = MutableLiveData<Events?>()
        if (eventId != null) {
            eventCollectionDB.document(eventId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val event = document.toObject(Events::class.java)
                        eventLiveData.value = event
                    }
                }
        }
        return eventLiveData
    }



    fun updateEvent(event: Events) {
        val eventRef = eventCollectionDB.document(event.id)
        eventRef.set(event)
            .addOnSuccessListener {
                Log.d("FirestoreViewModel", "Document updated successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreViewModel", "Error updating document", exception)
            }
    }
}