package com.example.fivefactorfinder

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.serialization.json.Json
import java.security.KeyStore


class PersonalityResults: AppCompatActivity() {
    lateinit var homebtn: com.google.android.material.button.MaterialButton
    lateinit var extrachart: com.github.mikephil.charting.charts.HorizontalBarChart
    lateinit var frameLayout: androidx.fragment.app.FragmentContainerView
    lateinit var navigationView: BottomNavigationView
    lateinit var personalityoutputmap: MutableMap<String, Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.test_results_layout)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        frameLayout = findViewById(R.id.frameContainer)
        navigationView = findViewById(R.id.bottomNavigationView)
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


        // Set the back button color

        /*homebtn = findViewById(R.id.hoemebuttun)
        homebtn.backgroundTintList = ColorStateList.valueOf(color)
        homebtn.setOnClickListener {
            onBackPressed()
        }*/

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null) { // Prevent reloading the fragment on screen rotation
            val bundle = Bundle()
            bundle.putSerializable("personalityMap", HashMap(personalityoutputmap)) // Convert to HashMap (Serializable)
            loadFragment(ExtroversionFragment(), bundle) // Load ExtroversionFragment by default
        }

        //loadFragment(ExtroversionFragment())


    /*extrachart = findViewById(R.id.extraversionchart)
        val dataEntriesExtroversion = mutableListOf<BarEntry>()
        val extraversionLabels = arrayOf<String>("EXTROVERSION",
            "Friendliness",
            "Gregariousness",
            "Assertiveness",
            "Activity Level",
            "Excitement-Seeking",
            "Cheerfulness"
        )
        val labelsExtroversion = mutableListOf<String>()
        var personalityResultsExtroversion = mutableMapOf<String, Int>()
        personalityoutputmap.get("EXTRAVERSION")?.let { personalityResultsExtroversion.put("EXTRAVERSION", it) }
        personalityoutputmap.get("Friendliness")?.let { personalityResultsExtroversion.put("Friendliness", it) }
        personalityoutputmap.get("Gregariousness")?.let { personalityResultsExtroversion.put("Gregariousness", it) }
        personalityoutputmap.get("Assertiveness")?.let { personalityResultsExtroversion.put("Assertiveness", it) }
        personalityoutputmap.get("Activity Level")?.let { personalityResultsExtroversion.put("Activity Level", it) }
        personalityoutputmap.get("Excitement-Seeking")?.let { personalityResultsExtroversion.put("Excitement-Seeking", it) }
        personalityoutputmap.get("Cheerfulness")?.let { personalityResultsExtroversion.put("Cheerfulness", it) }

        for ((label, value) in personalityResultsExtroversion) {
            dataEntriesExtroversion.add(BarEntry(dataEntriesExtroversion.size.toFloat(), value.toFloat()))
            labelsExtroversion.add(label)
        }
        dataEntriesExtroversion.reverse()
        labelsExtroversion.reverse()
        val barDataSetExtroversion = BarDataSet(dataEntriesExtroversion, "Extraversion Traits")
        barDataSetExtroversion.setValueTextColor(color)
        barDataSetExtroversion.setColors(color)
        val barDataExtroversion = BarData(barDataSetExtroversion)
        extrachart.data = barDataExtroversion
        extrachart.invalidate()*/


    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.extraversionmenu -> {
                    val bundle = Bundle()
                    bundle.putSerializable("personalityMap", HashMap(personalityoutputmap)) // Convert to HashMap (Serializable)
                    fragment = ExtroversionFragment()
                    loadFragment(fragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.agreeablenessmenu -> {
                    val bundle = Bundle()
                    bundle.putSerializable("personalityMap", HashMap(personalityoutputmap)) // Convert to HashMap (Serializable)
                    fragment = AgreeablenessFragment()
                    loadFragment(fragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.conscientiousnessmenu -> {
                    val bundle = Bundle()
                    bundle.putSerializable("personalityMap", HashMap(personalityoutputmap)) // Convert to HashMap (Serializable)
                    fragment = ConscientiousnessFragment()
                    loadFragment(fragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.neuroticismmenu -> {
                    val bundle = Bundle()
                    bundle.putSerializable("personalityMap", HashMap(personalityoutputmap)) // Convert to HashMap (Serializable)
                    fragment = NeuroticismFragment()
                    loadFragment(fragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.opennesstoexpmenu -> {
                    val bundle = Bundle()
                    bundle.putSerializable("personalityMap", HashMap(personalityoutputmap)) // Convert to HashMap (Serializable)
                    fragment = OpennessFragment()
                    loadFragment(fragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun loadFragment(fragment: Fragment, bundle: Bundle? = null) {
        if (bundle != null) {
            fragment.arguments = bundle
        }
        // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}