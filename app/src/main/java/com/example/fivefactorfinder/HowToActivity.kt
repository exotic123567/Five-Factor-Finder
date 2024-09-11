package com.example.fivefactorfinder

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class HowToActivity: AppCompatActivity() {
    lateinit var nextbtn: com.google.android.material.button.MaterialButton
    lateinit var prevbtn: com.google.android.material.button.MaterialButton
    lateinit var questionbtn: MaterialButton
    lateinit var handshakebtn: MaterialButton
    lateinit var rightorwrongbtn: MaterialButton
    lateinit var confidentialitybtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.how_to_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val theme: Resources.Theme = this.theme
        val colorAttr: Int = com.google.android.material.R.attr.colorOnSurface // Attribute for text color on primary background
        val typedValue = TypedValue()
        theme.resolveAttribute(colorAttr, typedValue, true)
        val color = typedValue.data // This will be the color based on the theme
        val intent = intent
        val nameenteredText = intent.getStringExtra("name")
        val countryenteredText = intent.getStringExtra("country")
        val ageenteredText = intent.getStringExtra("age")
        val sexenteredText = intent.getStringExtra("sex")

        Log.v("nameinhowto", "name recieved from previous activity through intent  : ${nameenteredText}")

        // Set the img buttons with no onclick listener's color (i.e; the images above each text describing it
        questionbtn = findViewById(R.id.questionicon)
        handshakebtn = findViewById(R.id.handshakeicon)
        rightorwrongbtn = findViewById(R.id.rightorwrongicon)
        confidentialitybtn = findViewById(R.id.lockicon)

        questionbtn.backgroundTintList = ColorStateList.valueOf(color)
        handshakebtn.backgroundTintList = ColorStateList.valueOf(color)
        rightorwrongbtn.backgroundTintList = ColorStateList.valueOf(color)
        confidentialitybtn.backgroundTintList = ColorStateList.valueOf(color)


        // Set the back button color

        prevbtn = findViewById(R.id.prevbtnhowto)
        prevbtn.backgroundTintList = ColorStateList.valueOf(color)
        prevbtn.setOnClickListener {
            onBackPressed()
        }
        nextbtn = findViewById(R.id.nextButtonhowto)

        nextbtn.setOnClickListener {
            val intent = Intent(this, Questions_Activity::class.java)
            intent.putExtra("name","${nameenteredText}")
            intent.putExtra("country","${countryenteredText}")
            intent.putExtra("age","${ageenteredText}")
            intent.putExtra("sex","${sexenteredText}")
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}