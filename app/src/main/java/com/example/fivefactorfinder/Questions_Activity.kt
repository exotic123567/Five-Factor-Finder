package com.example.fivefactorfinder

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import com.example.fivefactorfinder.EvaluatePersonality
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


data class Question(
    val questionId: Int,
    val questionHeadingContent: String,
    val questionSubtextContent: String,
    var answerValue: Int = 3
)

class Questions_Activity: AppCompatActivity() {
    lateinit var prevbtn: MaterialButton
    lateinit var nextbtn: MaterialButton
    lateinit var questionHeadingTxtView: TextView
    lateinit var questionSubtextTxtView: TextView
    lateinit var questionNumberInLayout: TextView
    private var currIndex = 0
    lateinit var answerslider: com.google.android.material.slider.Slider
    lateinit var questionimageview: com.google.android.material.imageview.ShapeableImageView
    val numofquestions = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.questions_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var personalityoutput = mutableMapOf<String, Int>()
        // Button color theming
        val theme: Resources.Theme = this.theme
        val colorAttr: Int = com.google.android.material.R.attr.colorOnSurface // Attribute for text color on primary background
        val typedValue = TypedValue()
        theme.resolveAttribute(colorAttr, typedValue, true)
        val color = typedValue.data // This will be the color based on the theme

        // Questions Loading from json file
        val jsonFile = resources.assets.open("questions.json")
        val reader = BufferedReader(InputStreamReader(jsonFile))
        val jsonText = reader.use { it.readText() }
        val gson = Gson()
        val listType = object : TypeToken<List<Question>>() {}.type
        val questionsList: List<Question> = gson.fromJson(jsonText, listType)

        // Load intent from previous activity
        val intent = intent
        val nameenteredText = intent.getStringExtra("name")
        val countryenteredText = intent.getStringExtra("country")
        val ageenteredText = intent.getStringExtra("age")
        val agefromuser = ageenteredText?.toIntOrNull()
        val sexenteredText = intent.getStringExtra("sex")
        val sexinlowercase = sexenteredText?.lowercase()

        Log.v("namefromintent", "name recieved from previous activity through intent  : ${nameenteredText}")

        // Image ids generated to access drawable image files, in order to display on screen
        val imageIds = mutableListOf<Int>()
        for (i in 1..numofquestions) {
            imageIds.add(resources.getIdentifier("q$i", "drawable", packageName))
        }
        Log.v("ImageIdCreation", "Image ids created are: ${imageIds}")

        // Accessing ui components
        questionimageview = findViewById(R.id.imageforquestions)
        questionNumberInLayout = findViewById((R.id.questionheadingtext))
        questionHeadingTxtView = findViewById(R.id.questionheading)
        questionSubtextTxtView = findViewById(R.id.questionsubtext)
        answerslider = findViewById(R.id.answeralider)
        var sliderValue = answerslider.value
        displayQuestionandImage(currIndex, questionsList, imageIds)


        // Set the button color
        prevbtn = findViewById(R.id.prevbtnquestions)
        prevbtn.backgroundTintList = ColorStateList.valueOf(color)


        prevbtn.setOnClickListener {
            if (currIndex == 0) {
                onBackPressed()
                return@setOnClickListener
            }
            currIndex--
            displayQuestionandImage(currIndex, questionsList, imageIds)
        }

        nextbtn = findViewById(R.id.nextButtonquestion)
        nextbtn.setOnClickListener {
            if (currIndex < numofquestions-1) {
                if (sliderValue >= -5.0f && sliderValue < -3.0f) {
                    questionsList[currIndex].answerValue = 1
                } else if (sliderValue >= -3.0f && sliderValue < -1.0f) {
                    questionsList[currIndex].answerValue = 2
                } else if (sliderValue >= -1.0f && sliderValue < 1.0f) {
                    questionsList[currIndex].answerValue = 3
                } else if (sliderValue >= 1.0f && sliderValue < 3.0f) {
                    questionsList[currIndex].answerValue = 4
                } else {
                    questionsList[currIndex].answerValue = 5
                }
                Log.v("Answer${currIndex+1}", "Answer${currIndex+1} value : ${questionsList[currIndex].answerValue}")
                currIndex++
                displayQuestionandImage(currIndex, questionsList, imageIds)
            } else if (currIndex == numofquestions - 1) {
                if (sliderValue >= -5.0f && sliderValue < -3.0f) {
                    questionsList[currIndex].answerValue = 1
                } else if (sliderValue >= -3.0f && sliderValue < -1.0f) {
                    questionsList[currIndex].answerValue = 2
                } else if (sliderValue >= -1.0f && sliderValue < 1.0f) {
                    questionsList[currIndex].answerValue = 3
                } else if (sliderValue >= 1.0f && sliderValue < 3.0f) {
                    questionsList[currIndex].answerValue = 4
                } else {
                    questionsList[currIndex].answerValue = 5
                }
                Log.v("Answer${currIndex+1}", "Answer${currIndex+1} value : ${questionsList[currIndex].answerValue}")
                val evaluator = EvaluatePersonality()
                CoroutineScope(Dispatchers.IO).launch {
                    val personalityoutput = withContext(Dispatchers.Main) {
                        if (agefromuser != null) {
                            if (sexinlowercase != null) {
                                val result = evaluator.evaluate(questionsList, sexinlowercase, agefromuser)
                                personalityoutput = result // Assign the return value to personalityoutput
                            }
                        }
                        personalityoutput
                    }

                    if (personalityoutput != null) {
                        val intent2 = Intent(this@Questions_Activity, PersonalityResults::class.java)
                        intent2.putExtra("name", "${nameenteredText}")
                        intent2.putExtra("country", "${countryenteredText}")
                        intent2.putExtra("age", "${ageenteredText}")
                        intent2.putExtra("sex", "${sexenteredText}")
                        val jsonString = withContext(Dispatchers.Default) {
                            Json.encodeToString(personalityoutput)
                        }
                        intent2.putExtra("resultjson", jsonString)
                        startActivity(intent2)
                    }
                }
            }

        }
        answerslider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Handle when the user starts touching the slider
                Log.d("Slider", "Start tracking touch")
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // Handle when the user releases their finger from the slider
                sliderValue = slider.value
                Log.d("Slider", "Value after releasing: $sliderValue")
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun displayQuestionandImage(index: Int, questionsList: List<Question>, imageList: MutableList<Int>) {
        if (index < numofquestions) {
            val question = questionsList[index]
            questionNumberInLayout.text = "Question "+"${(currIndex+1).toString()}"
            questionHeadingTxtView.text = question.questionHeadingContent
            questionSubtextTxtView.text = question.questionSubtextContent
            questionimageview.setImageResource(imageList[index])
            Log.v("ImageUpdate", "Image updated to resource id : ${imageList[currIndex]}")
        }

    }
}