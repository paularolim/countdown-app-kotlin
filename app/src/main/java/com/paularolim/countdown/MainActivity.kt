package com.paularolim.countdown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.paularolim.countdown.activities.DetailActivity
import com.paularolim.countdown.adapters.EventsAdapter
import com.paularolim.countdown.databinding.ActivityMainBinding
import com.paularolim.countdown.models.Event
import com.paularolim.countdown.utils.getDate
import com.paularolim.countdown.utils.getDaysUntil
import com.paularolim.countdown.viewmodels.EventsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EventsAdapter

    private val viewModel = EventsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = EventsAdapter(EventsAdapter.OnClickListener { event ->
            navigateToDetail(event)
        })

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)

        viewModel.events.observe(this, Observer {
            adapter.setList(it)
        })

        binding.btnAdd.setOnClickListener {
            navigateToForm()
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.getEvents()
    }

    private fun navigateToForm() {
        val intent = Intent(this, FormActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDetail(event: Event) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", event.id)
        intent.putExtra("title", event.title)
        intent.putExtra("date", getDate(event.date.toString()))
        intent.putExtra("days", getDaysUntil(event.date).toString())
        startActivity(intent)
    }
}