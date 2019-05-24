package com.bignerdranch.android.bignerdandroid.contoller

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bignerdranch.android.bignerdandroid.R
import com.bignerdranch.android.bignerdandroid.model.Question
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toast_view.*

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
        private val KEY_Cheater="tramposo"
        private val KEY_CORRECTAS="respuetasCorrectas"
        val TAG="Life"
        var  answer_is_true:Boolean = false
        private val REQUEST_CODE_CHEAT=0
    }

    private var mIsCheater:Boolean = false
    var mCurrentIndex:Int = 0
    var  respuestasOk:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        /*el onCreate se llama para instanciar el activity*/
        super.onCreate(savedInstanceState)


        //Log.d(TAG, "Estoy en onCreate")
        /*Se infla el xml y se crea el objeto View el layout*/
        setContentView(R.layout.activity_main)

        /*Actualizamos el valor de mCurrentIndex, para la persistencia de datos*/
        if (savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0)
            respuestasOk=savedInstanceState.getDouble(KEY_CORRECTAS)
            mIsCheater=savedInstanceState.getBoolean(KEY_Cheater)
        }

        /*Setemos el widged con su id, debemos importar este xml para poder hacer referencia a sus elementos por el id*/
        question_text_view.text="Esta debe ser una pregunta"

        //agregamos un escuchador al text_view
        question_text_view.setOnClickListener { v:View->
            mCurrentIndex=(mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        //creamos un array de objetos Question
        btn_next.setOnClickListener {

            if (mCurrentIndex==mQuestionBank.size-1){
                //calcular el promedio
                calcularPromedio()
                Toast.makeText(this@MainActivity,"Tu puntaje porcentual es ${calcularPromedio()}%", Toast.LENGTH_SHORT).show()
            }else{
            //mCurrentIndex=(mCurrentIndex + 1) % mQuestionBank.size
                mCurrentIndex++
            mIsCheater = false
           updateQuestion()
            habilitarBotones()
            }
        }
        updateQuestion()

        cheat_button.setOnClickListener {

            val answerIsTrue:Boolean=mQuestionBank[mCurrentIndex].mAnswerTrue

            //se crea el intent usando el metodo de CheatActivity
            val intent: Intent =CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //startActivity(intent)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        btn_prev.setOnClickListener {
            //mCurrentIndex=(mCurrentIndex -1) % mQuestionBank.size
                mCurrentIndex--
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


    //Se sobre escribe el metodo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        if (resultCode!=Activity.RESULT_OK) return
        if (requestCode== REQUEST_CODE_CHEAT){
            if (data==null) return
            mIsCheater=CheatActivity.wasAnswerShown(data)
        }
    }

    private fun calcularPromedio() :Int{
        return ((respuestasOk*100)/6).toInt()
    }

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

    private fun updateQuestion() {


        try {
            val question = mQuestionBank[mCurrentIndex].mTextResId
            question_text_view.setText(question)
        }catch (exception: ArrayIndexOutOfBoundsException ){
            var toastEx=Toast(applicationContext)
            var inflater=layoutInflater
            var layaut: View = inflater.inflate(R.layout.toast_view, findViewById(R.id.lytLayout))
            var txt:TextView=layaut.findViewById(R.id.txtMensaje)
            txt.text="Ya no hay mas preguntas provias"
            toastEx.duration=Toast.LENGTH_LONG
            toastEx.view=layaut
            toastEx.show()
        }
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,  "onSaveInstanceState")
        //guardamos este dato para la persistencia
        savedInstanceState?.putInt(KEY_INDEX,mCurrentIndex)
        savedInstanceState?.putDouble(KEY_CORRECTAS,respuestasOk)
        savedInstanceState?.putBoolean(KEY_Cheater,mIsCheater)
    }



    //verificar la respuesta
    @SuppressLint("ResourceAsColor")
    private fun checkAnswer(userPressedTrue:Boolean){
        answer_is_true= mQuestionBank[mCurrentIndex].mAnswerTrue
        var messageResId: Int

        //inhabilitamos el boton para que no ingrese mas de una respuesta
        inhabilitaBoton(userPressedTrue)

        if (mIsCheater) messageResId = R.string.judgment_toast;
        else{

            if (userPressedTrue == answer_is_true){
                respuestasOk++
                messageResId = R.string.correct_toast
            }
            else{
                messageResId=R.string.incorrect_toast
            }
        }
       val myToast= Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        myToast.setGravity(Gravity.TOP,0,150)
        myToast.view.setBackgroundColor(R.color.material_blue_grey_800)
        myToast.show()
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

}
