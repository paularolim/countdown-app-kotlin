package com.paularolim.countdown.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paularolim.countdown.models.Event

class CreateEventViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _error = MutableLiveData<Boolean>(false)

    val error get() = _error

    fun createEvent(event: Event) {
        db
            .collection("events")
            .add(event)
            .addOnSuccessListener {
                _error.postValue(false)
            }.addOnFailureListener {
                _error.postValue(true)
            }
    }
}