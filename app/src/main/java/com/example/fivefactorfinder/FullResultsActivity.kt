package com.example.fivefactorfinder

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.serialization.json.Json

class FullResultsActivity: AppCompatActivity() {
    lateinit var homebtn: com.google.android.material.button.MaterialButton
    lateinit var extrachart: com.github.mikephil.charting.charts.HorizontalBarChart
    lateinit var agreechart: HorizontalBarChart
    lateinit var consciencechart:  HorizontalBarChart
    lateinit var neuroticchart: HorizontalBarChart
    lateinit var opennesschart: HorizontalBarChart
    lateinit var personalityoutputmap: MutableMap<String, Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fullresultlayout)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        val dataEntriesExtroversion = mutableListOf<BarEntry>()
        val labelsExtroversion = mutableListOf<String>()
        val personalityResultsExtroversion = mutableMapOf<String, Int>()
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
        val personalityoutputjson = intent.getStringExtra("resultjson")
        personalityoutputmap = Json.decodeFromString(personalityoutputjson!!)

        extrachart = findViewById(R.id.extraversionchart)
        homebtn = findViewById(R.id.hoemebuttun)
        Log.v("personalityextroversionmap","${personalityoutputmap}")


        // Set the back button color
        homebtn.backgroundTintList = ColorStateList.valueOf(color)
        homebtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        personalityoutputmap?.get("EXTRAVERSION")
            ?.let { personalityResultsExtroversion.put("EXTRAVERSION", it) }
        personalityoutputmap?.get("Friendliness")
            ?.let { personalityResultsExtroversion.put("Friendliness", it) }
        personalityoutputmap?.get("Gregariousness")
            ?.let { personalityResultsExtroversion.put("Gregariousness", it) }
        personalityoutputmap?.get("Assertiveness")
            ?.let { personalityResultsExtroversion.put("Assertiveness", it) }
        personalityoutputmap?.get("Activity Level")
            ?.let { personalityResultsExtroversion.put("Activity Level", it) }
        personalityoutputmap?.get("Excitement-Seeking")
            ?.let { personalityResultsExtroversion.put("Excitement-Seeking", it) }
        personalityoutputmap?.get("Cheerfulness")
            ?.let { personalityResultsExtroversion.put("Cheerfulness", it) }

        personalityResultsExtroversion?.let { map ->
            for ((label, value) in map) {
                // ... (access label and value)
                dataEntriesExtroversion.add(BarEntry(dataEntriesExtroversion.size.toFloat(), value.toFloat()))
                labelsExtroversion.add(label)
            }
        }

        val barDataSetExtroversion = BarDataSet(dataEntriesExtroversion, "Extroversion Traits")
        barDataSetExtroversion.setValueTextColor(color) // Set text color
        barDataSetExtroversion.setColors(color) // Set bar color

        val barDataExtroversion = BarData(barDataSetExtroversion)
        Log.v("baradataext","${barDataExtroversion}")
        extrachart.data = barDataExtroversion
        extrachart.invalidate()
        extrachart.setDrawBorders(false)
        val xAxis = extrachart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labelsExtroversion)
        //extrachart.xAxis.isEnabled = false
        extrachart.axisLeft.isEnabled = false
        extrachart.axisRight.isEnabled = false
        extrachart.description.isEnabled = false
        extrachart.legend.isEnabled = false
    }
}