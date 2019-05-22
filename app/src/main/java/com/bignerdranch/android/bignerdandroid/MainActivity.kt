package com.bignerdranch.android.bignerdandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        /*el onCreate se llama para instanciar el activity*/
        super.onCreate(savedInstanceState)

        /*Se infla el xml y se crea el objeto View el layout*/
        setContentView(R.layout.activity_main)

        /*Setemos el widged con su id, debemos importar este xml para poder hacer referencia a sus elementos por el id*/
        question.text="Esta debe ser una pregunta"

        /*JAVA
        *   mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does nothing yet, but soon!
            }
});
        * */

        //**KOTLIN**
        //implementamos un listener para nuestro boton true
        btn_true.setOnClickListener {
            val mytoast =Toast.makeText(applicationContext, "True", Toast.LENGTH_LONG)
            mytoast.setGravity(Gravity.TOP,0,0)
            mytoast.show()
        }
        btn_false.setOnClickListener {
            val mytoast =Toast.makeText(applicationContext, "False", Toast.LENGTH_LONG)
            mytoast.setGravity(Gravity.TOP,0,0)

            mytoast.show()
        }

    }
}
