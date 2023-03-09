package com.paularolim.countdown

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paularolim.countdown.databinding.ActivityFormBinding
import com.paularolim.countdown.models.Event
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding

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

        val db = Firebase.firestore
        db
            .collection("events")
            .add(event)
            .addOnSuccessListener {
                binding.editTitle.setText("")
                Toast.makeText(this, "Evento salvo com sucesso!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar o evento", Toast.LENGTH_LONG).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSave.setOnClickListener {
            createEvent()
        }
    }
}