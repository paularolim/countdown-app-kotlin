package com.paularolim.countdown

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.paularolim.countdown.databinding.ActivityFormBinding
import com.paularolim.countdown.models.Event
import com.paularolim.countdown.viewmodels.FormViewModel
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding

    private val viewModel = FormViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val editMode = intent.getStringExtra("mode") == "edit"
        val date = intent.getStringExtra("date")
        val title = intent.getStringExtra("title")

        binding.btnDelete.visibility = if (editMode) View.VISIBLE else View.GONE
        binding.editTitle.setText(title ?: "")

        if (date != null && date.isNotBlank()) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date.toLong()
            binding.datePicker.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
            )
        }

        binding.btnSave.setOnClickListener { createEvent(editMode) }

        binding.btnDelete.setOnClickListener { deleteEvent() }

        viewModel.hasError.observe(this) { state ->
            val (called, error) = state
            if (called == true && error == true) {
                val message = if (editMode) "Erro ao excluir evento" else "Erro ao salvar o evento"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            } else if (called == true && error == false) {
                if (editMode) {
                    Toast.makeText(this, "Evento excluido com sucesso!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                } else {
                    binding.editTitle.setText("")
                    Toast.makeText(this, "Evento salvo com sucesso!", Toast.LENGTH_LONG).show()
                }
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

    private fun createEvent(editMode: Boolean) {
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

        if (editMode) {
            val id = intent.getStringExtra("id") ?: ""
            val event = Event(id = id, title = title, date = date)
            viewModel.updateEvent(event)
        } else {
            val event = Event(title = title, date = date)
            viewModel.createEvent(event)
        }
    }

    private fun deleteEvent() {
        val id = intent.getStringExtra("id") ?: ""

        AlertDialog
            .Builder(this)
            .setTitle("Excluir")
            .setMessage("Deseja realmente excluir este evento?")
            .setPositiveButton("Sim") { _, _ -> viewModel.deleteEvent(id) }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .setCancelable(true)
            .show()

    }
}