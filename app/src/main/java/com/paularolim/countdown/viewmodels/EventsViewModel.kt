package com.paularolim.countdown.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.paularolim.countdown.models.Event
import com.paularolim.countdown.repositories.EventsRepository

private const val TAG = "EventsViewModel"

class EventsViewModel(private val repository: EventsRepository) {
    private val _events = MutableLiveData<List<Event>>()

    val events get() = _events

    init {
        getEvents()
    }

    fun getEvents() {
        try {
            repository.getEvents { data ->
                _events.postValue(data)
            }
        } catch (exception: Error) {
            Log.d(TAG, "get failed with ", exception)
        }
    }
}