package com.paularolim.countdown.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paularolim.countdown.models.Event

private const val collection = "events"

class FormViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _called = MutableLiveData(false)
    private val _loading = MutableLiveData(false)
    private val _error = MutableLiveData<Boolean>(null)

    val hasError = PairMediatorLiveData(_called, _error)
    val loading get() = _loading

    fun createEvent(event: Event) {
        _loading.postValue(true)
        _called.postValue(true)

        db
            .collection(collection)
            .add(event)
            .addOnSuccessListener {
                _error.postValue(false)
                _loading.postValue(false)
            }.addOnFailureListener {
                _error.postValue(true)
                _loading.postValue(false)
            }
    }

    fun deleteEvent() {
        db.collection(collection)
    }
}