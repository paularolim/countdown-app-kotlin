package com.paularolim.countdown.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paularolim.countdown.FormActivity
import com.paularolim.countdown.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title = intent.getStringExtra("title") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val days = intent.getStringExtra("days") ?: ""

        setData(title, date, days)

        binding.btnDetailEdit.setOnClickListener { navigateToForm(title, date) }
    }

    private fun setData(title: String, date: String, days: String) {
        binding.txtDetailTitle.text = title
        binding.txtDetailDate.text = date
        binding.txtDetailDays.text = days
    }

    private fun navigateToForm(title: String, date: String) {
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra("mode", "edit")
        intent.putExtra("title", title)
        intent.putExtra("date", date)
        startActivity(intent)
    }
}