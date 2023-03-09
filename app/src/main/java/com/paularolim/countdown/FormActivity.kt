package com.paularolim.countdown

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paularolim.countdown.databinding.ActivityFormBinding
import com.paularolim.countdown.models.Event
import com.paularolim.countdown.viewmodels.FormViewModel
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding

    private val viewModel = FormViewModel()

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
        val event = Event(title = title, date = date)

        viewModel.createEvent(event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val editMode = intent.getStringExtra("mode") == "edit"
        binding.btnDelete.visibility = if (editMode) View.VISIBLE else View.GONE

        binding.btnSave.setOnClickListener {
            createEvent()
        }

        binding.btnDelete.setOnClickListener {
            deleteEvent()
        }

        viewModel.hasError.observe(this) { state ->
            val (called, error) = state
            if (called == true && error == true) {
                Toast.makeText(this, "Erro ao salvar o evento", Toast.LENGTH_LONG).show()
            } else if (called == true && error == false) {
                binding.editTitle.setText("")
                Toast.makeText(this, "Evento salvo com sucesso!", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.loading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
            binding.btnSave.visibility = if (loading) View.INVISIBLE else View.VISIBLE

            if (editMode) {
                binding.btnDelete.visibility = if (loading) View.INVISIBLE else View.VISIBLE
            }
        }
    }

    private fun deleteEvent() {
        viewModel.deleteEvent()
    }
}