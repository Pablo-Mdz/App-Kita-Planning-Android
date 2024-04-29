package de.syntax.androidabschluss.ui.daily.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import de.syntax.androidabschluss.ui.daily.data.model.DailyInfo
import de.syntax.androidabschluss.ui.daily.data.model.Notes
import de.syntax.androidabschluss.ui.events.data.model.Events

class DailyFirestoreViewModel (application: Application) : AndroidViewModel(application) {


    private val db = FirebaseFirestore.getInstance()
    //bring the data from DailyInfo collection
    private val cardInfoCollectionDB = db.collection("DailyInfo")
    //bring the data from Notes collection
    private val notesCollectionDB = db.collection("Notes")


    private val _cardInfo = MutableLiveData<QuerySnapshot>()
    val cardInfo: LiveData<QuerySnapshot>
        get() = _cardInfo


    private val _notes = MutableLiveData<QuerySnapshot>()
    val notes: LiveData<QuerySnapshot>
        get() = _notes


    init {
        cardInfoCollectionDB.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("FirestoreViewModel", "Error to get snapshot: $error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _cardInfo.value = snapshot
            } else {
                Log.e("FirestoreViewModel", "Snapshot is null")
            }
        }

        notesCollectionDB.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("FirestoreViewModel", "Error to get notes snapshot: $error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _notes.value = snapshot
            } else {
                Log.e("FirestoreViewModel", " notes Snapshot is null")
            }
        }
    }

    fun addDailyInfo(newDailyInfo: DailyInfo) {
        cardInfoCollectionDB.add(newDailyInfo)
    }
    fun addNotes(newNotes: Notes) {
        notesCollectionDB.add(newNotes)
    }

    fun deleteInfo(infoId: String) {
        cardInfoCollectionDB.document(infoId).delete()
    }

    fun deleteNotes(notesId: String) {
        notesCollectionDB.document(notesId).delete()
    }
}