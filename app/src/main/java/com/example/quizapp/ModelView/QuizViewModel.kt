package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.Model.Question

class QuizViewModel : ViewModel() {

    private val questions = listOf(
        Question("Who was the first President of the United States?", listOf("George Washington", "Thomas Jefferson", "Abraham Lincoln"), 0),
        Question("In what year did World War II end?", listOf("1945", "1941", "1939"), 0),
        Question("Who was known as the Iron Lady?", listOf("Margaret Thatcher", "Angela Merkel", "Hillary Clinton"), 0),
        Question("Which ancient civilization built the pyramids?", listOf("Egyptian", "Roman", "Greek"), 0),
        Question("What was the name of the ship that brought the Pilgrims to America in 1620?", listOf("Mayflower", "Santa Maria", "Endeavour"), 0),
        Question("Who discovered America in 1492?", listOf("Christopher Columbus", "Leif Erikson", "Vasco da Gama"), 0),
        Question("Which empire was ruled by Genghis Khan?", listOf("Mongol Empire", "Roman Empire", "Ottoman Empire"), 0),
        Question("What was the name of the first man to walk on the moon?", listOf("Neil Armstrong", "Buzz Aldrin", "Michael Collins"), 0),
        Question("Who was the British Prime Minister during World War II?", listOf("Winston Churchill", "Neville Chamberlain", "Clement Attlee"), 0),
        Question("Which country was the first to grant women the right to vote?", listOf("New Zealand", "United States", "United Kingdom"), 0)
    )

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question> = _currentQuestion

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> = _score

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex

    private val _isQuizOver = MutableLiveData(false)
    val isQuizOver: LiveData<Boolean> = _isQuizOver

    private val _answerResult = MutableLiveData<Boolean?>()
    val answerResult: LiveData<Boolean?> = _answerResult

    init{
        _currentQuestion.value = questions[_currentQuestionIndex.value!!]
    }

    fun checkAnswer(selectedIndex:Int){
        val correctIndex = currentQuestion.value!!.correctAnswerIndex
        if (selectedIndex == correctIndex) {
            _score.value = _score.value?.plus(1)
            _answerResult.value = true
        } else {
            _answerResult.value = false
        }
    }
    fun nextQuestion(){
        val nextIndex = _currentQuestionIndex.value!! +1
        if(nextIndex < questions.size){
            _currentQuestionIndex.value = nextIndex
            _currentQuestion.value = questions[nextIndex]
            _answerResult.value = null
        }else{
            _isQuizOver.value = true
        }
    }
    fun restartQuiz() {
        _currentQuestionIndex.value = 0
        _score.value = 0
        _currentQuestion.value = questions[0]
        _isQuizOver.value = false
        _answerResult.value = null
    }

}

