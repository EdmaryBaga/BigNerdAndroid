package com.bignerdranch.android.bignerdandroid.contoller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.bignerdranch.android.bignerdandroid.R
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {

    companion object{
        private val EXTRA_ANSWER_IS_TRUE="com.bignerdranch.android.bignerdandroid.contoller.answer_is_true"
        private val KEY_SHOW="cheat"
        private val EXTRA_ANSWER_SHOWN="com.bignerdranch.android.bignerdandroid.contoller.answer_shown"
        private var trampa:Boolean = false


        //este metodo sera usado por cualquier actividad que quiera pasar a CheatActivity
        fun newIntent(packageContext:Context, answerIsTrue:Boolean): Intent {
            var intent= Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }

        //metodo para obener algo que pueda usar MainActivity
        fun wasAnswerShown(result:Intent):Boolean{
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false)
        }
    }

    private var mAnswerIsTrue :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        if (savedInstanceState != null) trampa=savedInstanceState.getBoolean(KEY_SHOW)
        trampa_text.setText(trampa.toString())

        //recuperamos el valor del intent
        mAnswerIsTrue=intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false)

        //hacemos que nos muestre la soluion
        show_answer_button.setOnClickListener {
            if (mAnswerIsTrue) answer_text_view.setText(R.string.btn_true)
            else answer_text_view.setText(R.string.btn_false)
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown:Boolean){
        //enviamos datos al activity que llamo a CheatActivity(padre)
        var data= Intent()
        trampa=isAnswerShown
        data.putExtra(EXTRA_ANSWER_SHOWN, trampa)
        setResult(Activity.RESULT_OK,data)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(KEY_SHOW, trampa)
    }

}
