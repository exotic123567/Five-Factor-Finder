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
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NeuroticismFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NeuroticismFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var personalityMap: MutableMap<String, Int>? = null
    private lateinit var extrachart: com.github.mikephil.charting.charts.HorizontalBarChart
    private lateinit var homebtn: com.google.android.material.button.MaterialButton
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val serializableMap = it.getSerializable("personalityMap")
            // Check if it's not null before casting
            if (serializableMap != null) {
                personalityMap = serializableMap as MutableMap<String, Int>
                Log.v("mapreceived", "Personality map of neuroticism received in fragment")
                Log.v("mapneurotic","Map of neuroticism recieved is ${personalityMap}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_neuroticism, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("onViewCreated", "Inside onViewCreated")

        extrachart = view.findViewById(R.id.neuroticchart)
        homebtn = view.findViewById(R.id.hoemebuttun)
        textView = view.findViewById(R.id.neuroticscores)

        // Assume personalityMap is passed or initialized in some way

        val dataEntriesExtroversion = mutableListOf<BarEntry>()
        val labelsExtroversion = mutableListOf<String>()
        val personalityResultsExtroversion = mutableMapOf<String, Int>()
        val theme: Resources.Theme = requireActivity().theme
        val colorAttr: Int = com.google.android.material.R.attr.colorOnSurface // Attribute for text color on primary background
        val typedValue = TypedValue()
        theme.resolveAttribute(colorAttr, typedValue, true)
        val color = typedValue.data

        personalityMap?.get("NEUROTICISM")
            ?.let { personalityResultsExtroversion.put("NEUROTICISM", it) }
        personalityMap?.get("Anxiety")
            ?.let { personalityResultsExtroversion.put("Anxiety", it) }
        personalityMap?.get("Anger")
            ?.let { personalityResultsExtroversion.put("Anger", it) }
        personalityMap?.get("Depression")
            ?.let { personalityResultsExtroversion.put("Depression", it) }
        personalityMap?.get("Self-Consciousness")
            ?.let { personalityResultsExtroversion.put("Self-Consciousness", it) }
        personalityMap?.get("Immoderation")
            ?.let { personalityResultsExtroversion.put("Immoderation", it) }
        personalityMap?.get("Vulnerability")
            ?.let { personalityResultsExtroversion.put("Vulnerability", it) }

        for ((label, value) in personalityResultsExtroversion) {
            dataEntriesExtroversion.add(BarEntry(dataEntriesExtroversion.size.toFloat(), value.toFloat()))
            labelsExtroversion.add(label)
        }

        dataEntriesExtroversion.reverse()
        labelsExtroversion.reverse()

        val labels = mutableListOf("NEUROTICISM",
            "Anxiety", "Anger", "Depression", "Self-Consciousness", "Immoderation", "Vulnerability"
        )

        val stringBuilder = StringBuilder()
        stringBuilder.append("Your Neuroticism Traits:\n")
        for ((trait, value) in personalityResultsExtroversion) {
            stringBuilder.append("$trait: $value\n")
        }

        val personalityString = stringBuilder.toString()

        textView.text = personalityString

        val barDataSetExtroversion = BarDataSet(dataEntriesExtroversion, "Conscientiousness Traits")
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
        val xAxis = extrachart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        //extrachart.xAxis.isEnabled = false
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
         * @return A new instance of fragment NeuroticismFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NeuroticismFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}