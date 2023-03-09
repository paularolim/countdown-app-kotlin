package com.paularolim.countdown.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paularolim.countdown.databinding.EventListItemBinding
import com.paularolim.countdown.models.Event
import com.paularolim.countdown.utils.getDate
import com.paularolim.countdown.utils.getDaysUntil

class EventsAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    private var events = mutableListOf<Event>()

    inner class ViewHolder(val binding: EventListItemBinding) : RecyclerView.ViewHolder(binding.root)

    class OnClickListener(val clickListener: (event: Event) -> Unit) {
        fun onClick(event: Event) = clickListener(event)
    }

    fun setList(events: List<Event>) {
        this.events = events.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = events[position]
        holder.binding.txtTitle.text = item.title
        holder.binding.txtDate.text = getDate(item.date.toString())
        holder.binding.txtCountdown.text = "Faltam ${getDaysUntil(item.date)} dias"
        holder.binding.eventItem.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount() = events.size
}