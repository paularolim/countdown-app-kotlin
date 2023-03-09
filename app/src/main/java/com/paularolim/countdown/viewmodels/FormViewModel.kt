package com.paularolim.countdown.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paularolim.countdown.models.Event

private const val collection = "events"

class FormViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val ref = db.collection(collection)

    private val _called = MutableLiveData(false)
    private val _loading = MutableLiveData(false)
    private val _error = MutableLiveData<Boolean>(null)

    val hasError = PairMediatorLiveData(_called, _error)
    val loading get() = _loading

    fun createEvent(event: Event) {
        _loading.postValue(true)
        _called.postValue(true)

        val data = hashMapOf<String, Any>(
            "title" to event.title,
            "date" to event.date
        )

        ref
            .add(data)
            .addOnSuccessListener {
                _error.postValue(false)
                _loading.postValue(false)
            }.addOnFailureListener {
                _error.postValue(true)
                _loading.postValue(false)
            }
    }

    fun updateEvent(event: Event) {
        _loading.postValue(true)
        _called.postValue(true)

        val updates = hashMapOf<String, Any>(
            "title" to event.title,
            "date" to event.date
        )

        ref
            .document(event.id!!)
            .update(updates)
            .addOnSuccessListener {
                _error.postValue(false)
                _loading.postValue(false)
            }.addOnFailureListener {
                _error.postValue(true)
                _loading.postValue(false)
            }
    }

    fun deleteEvent(id: String) {
        _loading.postValue(true)
        _called.postValue(true)

        ref
            .document(id)
            .delete()
            .addOnSuccessListener {
                _error.postValue(false)
                _loading.postValue(false)
            }
            .addOnFailureListener {
                _error.postValue(true)
                _loading.postValue(false)
            }
    }
}