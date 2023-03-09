package com.paularolim.countdown.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paularolim.countdown.models.Event

class CreateEventViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _called = MutableLiveData(false)
    private val _error = MutableLiveData<Boolean>(null)

    val hasError = PairMediatorLiveData(_called, _error)

    fun createEvent(event: Event) {
        _called.postValue(true)
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