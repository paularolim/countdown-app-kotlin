package com.paularolim.countdown.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.paularolim.countdown.models.Event
import kotlinx.coroutines.tasks.await

private const val collection = "events"

class EventsRepository(firestore: FirebaseFirestore) {
    private val ref = firestore.collection(collection)

    suspend fun getEvents(): MutableList<Event> {
        val events: MutableList<Event> = mutableListOf()
        ref.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (item in task.result) {
                    val event = Event(
                        id = item.id,
                        title = item.data["title"] as String,
                        date = item.data["date"] as Long
                    )
                    events.add(event)
                }
            }
        }.await()
        return events
    }
}