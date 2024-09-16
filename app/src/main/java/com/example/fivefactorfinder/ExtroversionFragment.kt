package com.example.fivefactorfinder

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExtroversionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExtroversionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var personalityMap: MutableMap<String, Int>? = null
    private lateinit var extrachart: BarChart
    private lateinit var homebtn: com.google.android.material.button.MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val serializableMap = it.getSerializable("personalityMap")
            // Check if it's not null before casting
            if (serializableMap != null) {
                personalityMap = serializableMap as MutableMap<String, Int>
                Log.v("mapreceived", "Personality map of Extroversion received in fragment")
                Log.v("mapextriv","Map of extroversion recieved is ${personalityMap}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.v("Inside OnCreateView", "Inside OnCreateVIew and before returning layout inflater")
        val view = inflater.inflate(R.layout.fragment_extroversion, container, false)
        Log.v("ViewHierarchy", view.dumpViewHierarchy())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("onViewCreated", "Inside onViewCreated")

        extrachart = view.findViewById(R.id.extraversionchart)
        homebtn = view.findViewById(R.id.hoemebuttun)

        // Assume personalityMap is passed or initialized in some way

        val dataEntriesExtroversion = mutableListOf<BarEntry>()
        val labelsExtroversion = mutableListOf<String>()
        val personalityResultsExtroversion = mutableMapOf<String, Int>()
        val theme: Resources.Theme = requireActivity().theme
        val colorAttr: Int = com.google.android.material.R.attr.colorOnSurface // Attribute for text color on primary background
        val typedValue = TypedValue()
        theme.resolveAttribute(colorAttr, typedValue, true)
        val color = typedValue.data

        personalityMap?.get("EXTRAVERSION")
            ?.let { personalityResultsExtroversion.put("EXTRAVERSION", it) }
        personalityMap?.get("Friendliness")
            ?.let { personalityResultsExtroversion.put("Friendliness", it) }
        personalityMap?.get("Gregariousness")
            ?.let { personalityResultsExtroversion.put("Gregariousness", it) }
        personalityMap?.get("Assertiveness")
            ?.let { personalityResultsExtroversion.put("Assertiveness", it) }
        personalityMap?.get("Activity Level")
            ?.let { personalityResultsExtroversion.put("Activity Level", it) }
        personalityMap?.get("Excitement-Seeking")
            ?.let { personalityResultsExtroversion.put("Excitement-Seeking", it) }
        personalityMap?.get("Cheerfulness")
            ?.let { personalityResultsExtroversion.put("Cheerfulness", it) }

        for ((label, value) in personalityResultsExtroversion) {
            dataEntriesExtroversion.add(BarEntry(dataEntriesExtroversion.size.toFloat(), value.toFloat()))
            labelsExtroversion.add(label)
        }

        dataEntriesExtroversion.reverse()
        labelsExtroversion.reverse()

        val barDataSetExtroversion = BarDataSet(dataEntriesExtroversion, "Extraversion Traits")
        barDataSetExtroversion.setValueTextColor(color) // Set text color
        barDataSetExtroversion.setColors(color) // Set bar color

        homebtn.backgroundTintList = ColorStateList.valueOf(color)
        homebtn.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        val barDataExtroversion = BarData(barDataSetExtroversion)
        extrachart.data = barDataExtroversion
        extrachart.invalidate()
        extrachart.setDrawBorders(false)
        extrachart.xAxis.isEnabled = false
        extrachart.axisLeft.isEnabled = false
        extrachart.axisRight.isEnabled = false
        extrachart.description.isEnabled = false
        extrachart.legend.isEnabled = false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExtroversionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExtroversionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun View.dumpViewHierarchy(): String {
        val stringBuilder = StringBuilder()
        dumpView(this, stringBuilder, 0)
        return stringBuilder.toString()
    }

    private fun dumpView(view: View, stringBuilder: StringBuilder, level: Int) {
        for (i in 0 until level) {
            stringBuilder.append("  ")
        }
        stringBuilder.append(view.javaClass.simpleName)
        if (view is ViewGroup) {
            stringBuilder.append(" {")
            stringBuilder.append("\n")
            for (i in 0 until view.childCount) {
                dumpView(view.getChildAt(i), stringBuilder, level + 1)
            }
            for (i in 0 until level) {
                stringBuilder.append("  ")
            }
            stringBuilder.append("}")
        }
        stringBuilder.append("\n")
    }

}