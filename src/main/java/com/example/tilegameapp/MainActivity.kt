package com.example.tilegameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    // TODO: get some bitches
    // TODO: get rid of the "TileGameApp" bar, i think we have to switch to a "fullscreen activity"
    // TODO: figure out how to fill in the slotViews, maybe with a different imageView object
    fun createTileState() {
        // TODO: call from onCreate(), give random tile state thats checked for winnability
        // TODO: fill slotViews with their number (?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // this is the class and method instanced when app is opened
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val helpButton = findViewById<ImageButton>(R.id.helpButton)

        // TODO: create a temporary button to start and stop this timer
        val clockView = findViewById<TextView>(R.id.timerView)

        settingsButton.setOnClickListener() {
            settingsButton.animate().apply {
                duration = 50
                scaleXBy(0.25F)
                scaleYBy(0.25F)
            }.withEndAction() {
                settingsButton.animate().apply {
                    duration = 50
                    scaleXBy(-0.25F)
                    scaleYBy(-0.25F)
                }
            }.start()


            // opens window
            // stats about todays problem
            // your record (time)
            // dark mode vs light mode ?
            // donate button / pay to remove ads
        }

        helpButton.setOnClickListener() {
            helpButton.animate().apply {
                duration = 50
                scaleXBy(0.25F)
                scaleYBy(0.25F)
            }.withEndAction() {
                helpButton.animate().apply {
                    duration = 50
                    scaleXBy(-0.25F)
                    scaleYBy(-0.25F)
                }
            }.start()

            // opens window
            // instructions on how to play
            // include examples of tap-tile's and swipe-tile's swap mechanics
            // tell player one game is available per day
            // short example of solved game ie. 123456078 -> 123456708 -> 123456780 (W)
        }
    }
}