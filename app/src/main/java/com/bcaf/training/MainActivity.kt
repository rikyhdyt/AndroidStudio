package com.bcaf.training

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val defaultUsername = "admin"
    val defaultPassword = "admin"
    var counter = 0

    fun init(){

        btnLogin.setOnClickListener(View.OnClickListener {
            checkUsername(it)
//            var button = Button(applicationContext)
//            button.text= "Button" + counter++
//            containerFooter.addView(button)

        })

        txtForget.setOnClickListener(View.OnClickListener {
//            forgetPsw(it)
            finish()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    fun checkUsername(v : View){
        if (txtUsername.text.contentEquals(defaultUsername) && txtPassword.text.contentEquals(defaultPassword)){
            val intent = Intent(this, PortoActivity::class.java)
            intent.putExtra("username", txtUsername.text.toString())
            intent.putExtra("password", txtPassword.text.toString())
            startActivity(intent)

        }else{
            Toast.makeText(applicationContext, "Username atau password salah", Toast.LENGTH_LONG).show()
        }
    }

    fun forgetPsw(v : View){
        Toast.makeText(applicationContext, "Forget password !!!", Toast.LENGTH_LONG).show()
    }

//    override fun onStart(){
//        super.onStart()
//        println("start Action")
//    }

}