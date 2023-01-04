package com.example.zero_kata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
var singleuser=false
class MainActivity : AppCompatActivity() {

    lateinit var singlebtn : Button
    lateinit var multibtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singlebtn=findViewById(R.id.btnsingle)
        multibtn=findViewById(R.id.btnmulti)

        singlebtn.setOnClickListener {
            singleuser=true
            startActivity(Intent(this,GameplayActivity::class.java))
        }
        multibtn.setOnClickListener {
            singleuser=false
            startActivity(Intent(this,GameplayActivity::class.java))
        }
    }
}