package com.bcaf.training

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_menu.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.Period
import java.time.Year
import java.util.*

class MenuActivity : AppCompatActivity() {
    var username:String?=""
    var password:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        animateText()
        val datas : Bundle? = intent.extras
        username = datas?.getString("username","")
        password = datas?.getString("password", "")

        txtHello.text = "Selamat Datang $username"
        animateText()

        btnPickDate.setOnClickListener(View.OnClickListener {
            pickDate(it) })

        btnDial.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data= Uri.parse("tel:085157271882")
            }
            startActivity(intent)
        })
    }


    fun animateText(){
        val anim = AlphaAnimation(0.0f, 1f)
        anim.duration=1000
        anim.startOffset=1000
        anim.repeatMode= Animation.REVERSE
        anim.repeatCount=Animation.INFINITE
        txtHello.startAnimation(anim)
    }

    fun pickDate(view : View){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSet (
                view: DatePicker, year:Int, monthOfYear: Int, dayOfMonth: Int
            ){
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, monthOfYear)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd/MMM/yyy"
                val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                txtTglLahir.text=sdf.format(c.getTime())

//                var tahunLahir = c.get(Calendar.YEAR)
//                var tahunSekarang = Calendar.getInstance().get(Calendar.YEAR)
//                var umur = tahunSekarang - tahunLahir

                val umur = Period.between(LocalDate.of(year, month, dayOfMonth), LocalDate.now())

                editUmur.setText("${umur.years} tahun ${umur.months} bulan")
            }
        }

        DatePickerDialog(this,
        dateSetListener,
        c.get(Calendar.YEAR),
        c.get(Calendar.MONTH),
        c.get(Calendar.DAY_OF_MONTH)).show()
    }

}