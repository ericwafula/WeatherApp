package tech.ericwathome.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tech.ericwathome.weatherapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}