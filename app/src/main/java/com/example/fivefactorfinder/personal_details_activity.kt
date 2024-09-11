package com.example.fivefactorfinder

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class personal_details_activity : AppCompatActivity() {
    lateinit var nextbtn: com.google.android.material.button.MaterialButton
    lateinit var prevbtn: com.google.android.material.button.MaterialButton
    lateinit var personname: String
    lateinit var country: String
    lateinit var age_str: String
    lateinit var sex_str: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.add_personal_details)
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




        // Set the button color

        prevbtn = findViewById(R.id.prevbtnwaitamin)
        prevbtn.backgroundTintList = ColorStateList.valueOf(color)
        prevbtn.setOnClickListener {
            onBackPressed()
        }
        nextbtn = findViewById(R.id.nextButtonwaitamin)

        nextbtn.setOnClickListener {
            val nametextInputLayout = findViewById<TextInputLayout>(R.id.nameinput) // Get the TextInputLayout
            val nametextInputEditText = nametextInputLayout.editText as TextInputEditText // Access the EditText within

            var nameenteredText = nametextInputEditText.text.toString() // Get the entered text

            val countrytextInputLayout = findViewById<TextInputLayout>(R.id.countryinput) // Get the TextInputLayout
            val countrytextInputEditText = countrytextInputLayout.editText as TextInputEditText // Access the EditText within

            var countryenteredText = countrytextInputEditText.text.toString() // Get the entered text

            val agetextInputLayout = findViewById<TextInputLayout>(R.id.agetext) // Get the TextInputLayout
            val agetextInputEditText = agetextInputLayout.editText as TextInputEditText // Access the EditText within

            var ageenteredText = agetextInputEditText.text.toString() // Get the entered text

            val sextextInputLayout = findViewById<TextInputLayout>(R.id.sextext) // Get the TextInputLayout
            val sextextInputEditText = sextextInputLayout.editText as TextInputEditText // Access the EditText within

            var sexenteredText = sextextInputEditText.text.toString() // Get the entered text

            if (sexenteredText in listOf("Male","Female","male","female") && ageenteredText.toIntOrNull() != null && !nameenteredText.isEmpty() && !countryenteredText.isEmpty()) {
                val intent = Intent(this, HowToActivity::class.java)
                intent.putExtra("name","${nameenteredText}")
                intent.putExtra("country","${countryenteredText}")
                intent.putExtra("age","${ageenteredText}")
                intent.putExtra("sex","${sexenteredText}")
                startActivity(intent)
            }
            else {
                Toast.makeText(this,"Please enter approporiate values for given fields...", Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}