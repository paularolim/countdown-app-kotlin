package com.paularolim.countdown.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paularolim.countdown.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val days = intent.getStringExtra("days")
        binding.txtDetailTitle.text = title
        binding.txtDetailDate.text = date
        binding.txtDetailDays.text = days
    }
}