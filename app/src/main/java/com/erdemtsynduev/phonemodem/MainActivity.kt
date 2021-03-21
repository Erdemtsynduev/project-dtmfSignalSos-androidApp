package com.erdemtsynduev.phonemodem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startCaller()
    }

    private fun startCaller() {
        val numberString = "89875530342,,,,1111222"
        val number: Uri = Uri.parse("tel:$numberString")
        val dial = Intent(Intent.ACTION_CALL, number)
        startActivity(dial)
    }
}