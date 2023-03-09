package com.paularolim.countdown.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paularolim.countdown.FormActivity
import com.paularolim.countdown.databinding.ActivityDetailBinding
import com.paularolim.countdown.utils.getDate

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id = intent.getStringExtra("id") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val date = intent.getStringExtra("date") ?: ""
        val days = intent.getStringExtra("days") ?: ""

        setData(title, date, days)

        binding.btnDetailEdit.setOnClickListener { navigateToForm(id, title, date) }
    }

    private fun setData(title: String, date: String, days: String) {
        binding.txtDetailTitle.text = title
        binding.txtDetailDate.text = getDate(date)
        binding.txtDetailDays.text = days
    }

    private fun navigateToForm(id: String, title: String, date: String) {
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra("mode", "edit")
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        intent.putExtra("date", date)
        startActivity(intent)
    }
}