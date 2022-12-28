package com.bcaf.training

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_porto.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class PortoActivity : AppCompatActivity() {

    companion object {
        private val REQUEST_CODE_PERMISSION=999
        private val CAMERA_REQUEST_CAPTURE=1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_porto)

        btnCall.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data= Uri.parse("tel:085157271882")
            }
            startActivity(intent)
        })

        btnMail.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "type/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("rikyhd015@gmail.com")) // recipients
                putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                putExtra(Intent.EXTRA_TEXT, "Email message text")
            }
            startActivity(intent)
        })

        btnMaps.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse(
                    "https://www.google.co.id/maps/place/BCA+KCU+PONDOK+INDAH/@-6.2869899,106.7768287,17z/data=!3m1!4b1!4m5!3m4!1s0x2e69f1cee44fffff:0xaf293259891424bc!8m2!3d-6.2870645!4d106.7789387"
                    ) )
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        })

        btnCamera.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){

                    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, REQUEST_CODE_PERMISSION)
                }
                else{
                    captureCamera()
                }
            }
        })

        btnCalculator.setOnClickListener(View.OnClickListener{
            toCalculator(it)
        })

        btnMenu.setOnClickListener(View.OnClickListener{
            toMenu(it)
        })
    }

    fun toCalculator(view: View){
        val intent = Intent(this, Calculator::class.java)
        startActivity(intent)
    }

    fun toMenu(view: View){
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    fun captureCamera(){
        val takeCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takeCamera, CAMERA_REQUEST_CAPTURE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_PERMISSION -> {
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    captureCamera();
                }else{
                    Toast.makeText(this, "Sorry permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data:Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== CAMERA_REQUEST_CAPTURE && resultCode == RESULT_OK){
            val bitmapImage = data?.extras?.get("data") as Bitmap
            btnCamera.setImageBitmap(bitmapImage)
            saveImage(bitmapImage)
        }
    }

    fun saveImage(bitmap:Bitmap){
        val tanggal = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val extStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
        val namaFile = extStorage + "/BCAF" + tanggal + ".png"
        var file:File?=null

        file=File(namaFile)
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,50, bos)
        val bitmapData = bos.toByteArray()

        val fos = FileOutputStream(file)

        fos.write(bitmapData)
        fos.flush()
        fos.close()
    }
}