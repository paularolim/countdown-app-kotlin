package com.paularolim.countdown.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paularolim.countdown.models.Event

const val TAG = "EventsViewModel"

class EventsViewModel {
    private val _events = MutableLiveData<List<Event>>()

    val events get() = _events

    init {
        getEvents()
    }

    fun getEvents() {
        val db = Firebase.firestore
        val docRef = db.collection("events")

        docRef
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot != null) {
                    var list: MutableList<Event> = mutableListOf()
                    querySnapshot.forEach { document ->
                        list.add(
                            Event(
                                document.data["title"].toString(),
                                document.data["date"].toString().toLong()
                            )
                        )
                    }
                    _events.postValue(list)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}