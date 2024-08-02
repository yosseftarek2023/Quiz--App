package com.example.quizapp.Model

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)


