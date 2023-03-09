package com.paularolim.countdown.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.paularolim.countdown.models.Event

private const val collection = "events"

class EventsRepository(firestore: FirebaseFirestore) {
    private val ref = firestore.collection(collection)

    private val events: MutableList<Event> = mutableListOf()

    fun getEvents(callback: (List<Event>) -> Unit) {
        ref.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        events.add(
                            Event(
                                id = document.id,
                                title = document.data["title"].toString(),
                                date = document.data["date"].toString().toLong()
                            )
                        )
                    }
                    callback(events)
                }
            }
            .addOnFailureListener {
                throw Error(it)
            }
    }
}