package com.bcaf.training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_calculator.*
import org.mariuszgromada.math.mxparser.Expression

class Calculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
    }

    fun onDigitPress(view:View){
        txtInput.append((view as Button).text)
    }

    fun onClear(view: View){
        txtInput.setText("")
    }

    fun onOperator(view: View){
        if (txtInput.text.toString().substring(txtInput.text.toString().length-1).equals(null)){
            txtInput.append((view as Button).text)

        }else{
            if (txtInput.text.toString().substring(txtInput.text.toString().length-1).equals(".")){
                txtInput.setText(txtInput.text.toString().substring(0,txtInput.text.toString().length-1))
            }else{
                txtInput.append((view as Button).text)}
        }
    }

    fun onCalc(view: View){
        var e = Expression(txtInput.text.toString())
        txtInput.setText(e.calculate().toString())
    }
}