package com.example.quizapp.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.quizapp.QuizViewModel
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.Model.Question

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private var selectedButtonIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizViewModel.currentQuestion.observe(this) { question ->
            displayQuestion(question)
        }

        quizViewModel.answerResult.observe(this) { result ->
            result?.let {
                if (it) {
                    correctButton(quizViewModel.currentQuestion.value!!.correctAnswerIndex)
                } else {
                    wrongButton(selectedButtonIndex)
                    correctButton(quizViewModel.currentQuestion.value!!.correctAnswerIndex)
                }
                binding.questionText.postDelayed({
                    quizViewModel.nextQuestion()
                }, 1000)
            }
        }

        quizViewModel.isQuizOver.observe(this) { isOver ->
            if (isOver) {
                showResult()
                binding.restartButton.isEnabled = true
            }
        }

        binding.answerButton1.setOnClickListener { checkAnswer(0) }
        binding.answerButton2.setOnClickListener { checkAnswer(1) }
        binding.answerButton3.setOnClickListener { checkAnswer(2) }
        binding.restartButton.setOnClickListener { restartQuiz() }
    }

    private fun correctButton(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.answerButton1.setBackgroundColor(Color.GREEN)
            1 -> binding.answerButton2.setBackgroundColor(Color.GREEN)
            2 -> binding.answerButton3.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButton(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.answerButton1.setBackgroundColor(Color.RED)
            1 -> binding.answerButton2.setBackgroundColor(Color.RED)
            2 -> binding.answerButton3.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        binding.answerButton1.setBackgroundColor(Color.BLACK)
        binding.answerButton2.setBackgroundColor(Color.BLACK)
        binding.answerButton3.setBackgroundColor(Color.BLACK)
    }

    private fun displayQuestion(question: Question) {
        binding.questionText.text = question.question
        binding.answerButton1.text = question.options[0]
        binding.answerButton2.text = question.options[1]
        binding.answerButton3.text = question.options[2]
        resetButtonColors()
    }

    private fun showResult() {
        Toast.makeText(this, "Your Result: ${quizViewModel.score.value}", Toast.LENGTH_SHORT).show()
    }

    private fun checkAnswer(selectedIndex: Int) {
        selectedButtonIndex = selectedIndex
        quizViewModel.checkAnswer(selectedIndex)
    }

    private fun restartQuiz() {
        quizViewModel.restartQuiz()
        binding.restartButton.isEnabled = false
        selectedButtonIndex = -1

    }

}
