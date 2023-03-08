package com.paularolim.countdown

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paularolim.countdown.databinding.ActivityFormBinding

class FormActivity:AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSave.setOnClickListener {
            binding.editTitle.setText("")
            binding.editTextDate.setText("")
            Toast.makeText(this, "Event saved", Toast.LENGTH_LONG).show()
        }
    }
}