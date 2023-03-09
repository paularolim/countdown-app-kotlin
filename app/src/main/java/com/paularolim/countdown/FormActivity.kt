package com.paularolim.countdown

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paularolim.countdown.databinding.ActivityFormBinding
import com.paularolim.countdown.models.Event
import com.paularolim.countdown.viewmodels.CreateEventViewModel
import java.util.*
import androidx.lifecycle.Observer

class FormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding

    private val viewModel = CreateEventViewModel()

    private fun createEvent() {
        val title = binding.editTitle.text.toString()

        if (title.trim() == "") {
            binding.editTitle.error = "Preencha o campo"
            return
        }

        val year = binding.datePicker.year
        val month = binding.datePicker.month
        val day = binding.datePicker.dayOfMonth

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, 0, 0, 0)

        val date = calendar.timeInMillis
        val event = Event(title, date)

        viewModel.createEvent(event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSave.setOnClickListener {
            createEvent()
        }

        viewModel.error.observe(this, Observer {
            if (it == true) {
                Toast.makeText(this, "Erro ao salvar o evento", Toast.LENGTH_LONG).show()
            } else {
                binding.editTitle.setText("")
                Toast.makeText(this, "Evento salvo com sucesso!", Toast.LENGTH_LONG).show()
            }
        })
    }
}