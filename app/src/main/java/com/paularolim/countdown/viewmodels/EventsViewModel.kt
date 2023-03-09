package com.paularolim.countdown.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paularolim.countdown.models.Event
import com.paularolim.countdown.repositories.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "EventsViewModel"

class EventsViewModel(private val repository: EventsRepository) : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()

    val events get() = _events

    init {
        getEvents()
    }

    fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getEvents()
                withContext(Dispatchers.Main) {
                    _events.postValue(response)
                }
            } catch (exception: Exception) {
                Log.i(TAG, ">>>>>> $exception")
            }
        }
    }
}