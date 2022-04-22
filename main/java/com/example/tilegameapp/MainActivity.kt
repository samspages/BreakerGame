package com.example.tilegameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // this is the class and method instanced when app is opened
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsButton = findViewById<Button>(R.id.settingsButton)
        val helpButton = findViewById<Button>(R.id.helpButton)
        val numblerView = findViewById<TextView>(R.id.numblerView)

        settingsButton.setOnClickListener() {
            // opens window
            // stats about todays problem
            // your record (time)
            // dark mode vs light mode ?
            // donate button / pay to remove ads
        }

        helpButton.setOnClickListener() {
            // opens window
            // instructions on how to play
            // include examples of tap-tile's and swipe-tile's swap mechanics
            // tell player one game is available per day
            // short example of solved game ie. 123456078 -> 123456708 -> 123456780 (W)
        }
    }
}