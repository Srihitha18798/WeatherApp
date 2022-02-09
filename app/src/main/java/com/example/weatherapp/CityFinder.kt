package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class CityFinder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_finder)
        val editText = findViewById<EditText>(R.id.searchCity)
        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        editText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                val newCity = editText.text.toString()
                val intent = Intent(this@CityFinder, MainActivity::class.java)
                intent.putExtra("City", newCity)
                startActivity(intent)


                return false
            }

        })
    }
}