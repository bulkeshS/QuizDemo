package com.example.quizapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Collections

class MainActivity : AppCompatActivity() {
    private var questionItems: ArrayList<QuestionItem>?=null
    private var currentQuestion = 0
    private var correct = 0
    private var wrong = 0
    private  var mBinding: com.example.quizapp.databinding.ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // get all questins
        loadAllQuestions()
        // shuffel all the question if you want
        questionItems!!.shuffle()
        // load first question
        setQuestionScreen(currentQuestion)

        mBinding!!.answer1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                questionItems!![currentQuestion].isChecked = true
                mBinding!!.answer2.isChecked = false
                mBinding!!.answer3.isChecked = false
                mBinding!!.answer4.isChecked = false

                }
            }

            mBinding!!.answer2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    mBinding!!.answer1.isChecked = false
                    mBinding!!.answer3.isChecked = false
                    mBinding!!.answer4.isChecked = false
                    questionItems!![currentQuestion].isChecked = true
                }
            }

            mBinding!!.answer3.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    questionItems!![currentQuestion].isChecked = true
                    mBinding!!.answer1.isChecked = false
                    mBinding!!.answer2.isChecked = false
                    mBinding!!.answer4.isChecked = false

                }
            }

            mBinding!!.answer4.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    questionItems!![currentQuestion].isChecked = true
                    mBinding!!.answer1.isChecked = false
                    mBinding!!.answer2.isChecked = false
                    mBinding!!.answer3.isChecked = false


                }
            }
            mBinding!!.tvNext.setOnClickListener {
                Toast.makeText(this@MainActivity, "clciked", Toast.LENGTH_SHORT).show()
                mBinding!!.answer1.isChecked = false
                mBinding!!.answer2.isChecked = false
                mBinding!!.answer3.isChecked = false
                mBinding!!.answer4.isChecked = false
                if (questionItems!![currentQuestion].answer1 == questionItems!![currentQuestion].correct) {
                    // correct
                    correct++

                } else {
                    //wrong

                    wrong++


                }
                if (currentQuestion < questionItems!!.size - 1) {
                    currentQuestion++
                    setQuestionScreen(currentQuestion)
                } else {
                    // game over
                    val intent = Intent(applicationContext, EndActivity::class.java)
                    intent.putExtra("correct", correct)
                    intent.putExtra("wrong", wrong)
                    startActivity(intent)
                    finish()

                }

            }
        }



    // set question to the screen
    private fun setQuestionScreen(number: Int) {
        mBinding!!.question.text = questionItems!![number].question
        mBinding!!.answer1.text = questionItems!![number].answer1
        mBinding!!.answer2.text = questionItems!![number].answer2
        mBinding!!.answer3.text = questionItems!![number].answer3
        mBinding!!.answer4.text = questionItems!![number].answer4
    }

    // make list with all questions
    private fun loadAllQuestions() {
        questionItems = ArrayList()

        //laod all question into the json String
        val jsonStr = loadJSONFromAsserts("questions.json")

        //laod all data into the list
        try {
            val jsonObj = JSONObject(jsonStr)
            val questions = jsonObj.getJSONArray("questions")
            for (i in 0 until questions.length()) {
                val question = questions.getJSONObject(i)

                val questionString = question.getString("question")
                val answer1String = question.getString("answer1")
                val answer2String = question.getString("answer2")
                val answer3String = question.getString("answer3")
                val answer4String = question.getString("answer4")
                val correctString = question.getString("correct")

                questionItems!!.add(QuestionItem(
                        questionString,
                        answer1String,
                        answer2String,
                        answer3String,
                        answer4String,
                        correctString,false
                ))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
    fun Context.loadJSONFromAsserts(fileName: String): String {
        return applicationContext.assets.open(fileName).bufferedReader().use { reader ->
            reader.readText()
        }
    }

}
