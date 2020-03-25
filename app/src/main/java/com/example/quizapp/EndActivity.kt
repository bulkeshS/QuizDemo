package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.TextView

class EndActivity : AppCompatActivity() {

    private var tv_result: TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        tv_result = findViewById(R.id.result)
        val correct = intent.getIntExtra("correct", 0)
        val wrong = intent.getIntExtra("wrong", 0)

        tv_result!!.text = "Correct $correct\nWrong $wrong"
    }
}
