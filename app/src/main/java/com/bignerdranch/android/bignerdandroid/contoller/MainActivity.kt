package com.bignerdranch.android.bignerdandroid.contoller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.bignerdranch.android.bignerdandroid.R
import com.bignerdranch.android.bignerdandroid.model.Question
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mQuestionBank= arrayOf(Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var mCurrentIndex:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        /*el onCreate se llama para instanciar el activity*/
        super.onCreate(savedInstanceState)

        /*Se infla el xml y se crea el objeto View el layout*/
        setContentView(R.layout.activity_main)

        /*Setemos el widged con su id, debemos importar este xml para poder hacer referencia a sus elementos por el id*/
        question_text_view.text="Esta debe ser una pregunta"

        //agregamos un escuchador al text_view
        question_text_view.setOnClickListener { v:View->
            mCurrentIndex=(mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        //creamos un array de objetos Question

        btn_next.setOnClickListener {
            mCurrentIndex=(mCurrentIndex + 1) % mQuestionBank.size
           updateQuestion()
        }
        updateQuestion()

        btn_prev.setOnClickListener {
            mCurrentIndex=(mCurrentIndex -1) % mQuestionBank.size
            updateQuestion()
        }


        //**KOTLIN**
        //implementamos un listener para nuestro boton true
        btn_true.setOnClickListener {
            checkAnswer(true);
        }
        btn_false.setOnClickListener {
            checkAnswer(false);
        }

    }

    private fun updateQuestion(){
       var question=  mQuestionBank[mCurrentIndex].mTextResId
        question_text_view.setText(question)
    }

    //verificar la respuesta
    private fun checkAnswer(userPressedTrue:Boolean){
        var answerIsTrue= mQuestionBank[mCurrentIndex].mAnswerTrue

        var messageResId=0

        if (userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast
        }
        else{
            messageResId=R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
