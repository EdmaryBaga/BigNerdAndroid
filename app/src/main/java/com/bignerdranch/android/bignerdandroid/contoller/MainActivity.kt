package com.bignerdranch.android.bignerdandroid.contoller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    //para viable staticas

    companion object{
        private val KEY_INDEX="index"
    }

    val TAG="Life"

    var mCurrentIndex:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        /*el onCreate se llama para instanciar el activity*/
        super.onCreate(savedInstanceState)


        //Log.d(TAG, "Estoy en onCreate")

        /*Se infla el xml y se crea el objeto View el layout*/
        setContentView(R.layout.activity_main)

        /*Actualizamos el valor de mCurrentIndex, para la persistencia de datos*/
        if (savedInstanceState!=null) mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0)

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
            habilitarBotones()
        }
        updateQuestion()

        btn_prev.setOnClickListener {
            mCurrentIndex=(mCurrentIndex -1) % mQuestionBank.size
            updateQuestion()
            habilitarBotones()
        }


        //**KOTLIN**
        //implementamos un listener para nuestro boton true
        btn_true.setOnClickListener {
            checkAnswer(true)
            //btn_false.isClickable=false

        }
        btn_false.setOnClickListener {
            checkAnswer(false)
            //btn_true.isClickable=false
        }

    }

   /* override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"estoy en onResume")
    }

    override fun onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy called")
    }
*/

    fun habilitarBotones(){
        btn_false.isEnabled=true
        btn_true.isEnabled=true
    }

    fun inhabilitaBoton(btn:Boolean){
        when(btn){
            true ->btn_false.isEnabled=false
            else ->btn_true.isEnabled=false
        }
    }

    private fun updateQuestion(){

       try {
           var question=  mQuestionBank[mCurrentIndex].mTextResId
           question_text_view.setText(question)
       } catch (t: Throwable ) {
           // Log a message at "error" log level, along with an exception stack trace
           Log.e(TAG, "Index was out of bounds", t)}

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,  "onSaveInstanceState")
        //guardamos este dato para la persistencia
        savedInstanceState?.putInt(KEY_INDEX,mCurrentIndex)

    }



    //verificar la respuesta
    private fun checkAnswer(userPressedTrue:Boolean){
        var answerIsTrue= mQuestionBank[mCurrentIndex].mAnswerTrue

        var messageResId=0
        //inhabilitamos el boton para que no ingrese mas de una respuesta
        inhabilitaBoton(userPressedTrue)

        if (userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast
        }
        else{
            messageResId=R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }
}
