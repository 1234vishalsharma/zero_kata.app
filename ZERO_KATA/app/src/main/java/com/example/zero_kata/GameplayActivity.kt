package com.example.zero_kata

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess

var playerturn=true

class GameplayActivity : AppCompatActivity() {

    lateinit var player1Tv : TextView
    lateinit var player2Tv : TextView

    lateinit var box1 : Button
    lateinit var box2 : Button
    lateinit var box3 : Button
    lateinit var box4 : Button
    lateinit var box5 : Button
    lateinit var box6 : Button
    lateinit var box7 : Button
    lateinit var box8 : Button
    lateinit var box9 : Button
    lateinit var resetbtn : Button

    var player1count=0
    var player2count=0

    var player1= ArrayList<Int>()
    var player2= ArrayList<Int>()
    var emptycells= ArrayList<Int>()

    var activeuser=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplay)

        player1Tv=findViewById(R.id.player1id)
        player2Tv=findViewById(R.id.player2id)

        box1=findViewById(R.id.btnbox1)
        box2=findViewById(R.id.btnbox2)
        box3=findViewById(R.id.btnbox3)
        box4=findViewById(R.id.btnbox4)
        box5=findViewById(R.id.btnbox5)
        box6=findViewById(R.id.btnbox6)
        box7=findViewById(R.id.btnbox7)
        box8=findViewById(R.id.btnbox8)
        box9=findViewById(R.id.btnbox9)

        resetbtn=findViewById(R.id.btnreset)
        resetbtn.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        player1.clear()
        player2.clear()
        emptycells.clear()
        activeuser=1
        for (i in 1..9){
            var btnselector : Button
            btnselector=when(i){
                1->box1
                2->box2
                3->box3
                4->box4
                5->box5
                6->box6
                7->box7
                8->box8
                9->box9
                else->{
                    box1
                }
            }
            btnselector.isEnabled=true
            btnselector.text=""
            player1Tv.text="Player 1 : $player1count"
            player2Tv.text="Player 2 : $player2count"
        }
    }

    fun buttonclicked(view: View) {
        if(playerturn){
            val but=view as Button
            var cellID=0
            when(but.id){
                R.id.btnbox1 -> cellID=1
                R.id.btnbox2 -> cellID=2
                R.id.btnbox3 -> cellID=3
                R.id.btnbox4 -> cellID=4
                R.id.btnbox5 -> cellID=5
                R.id.btnbox6 -> cellID=6
                R.id.btnbox7 -> cellID=7
                R.id.btnbox8 -> cellID=8
                R.id.btnbox9 -> cellID=9
            }
            playerturn=false
            Handler().postDelayed({ playerturn=true},600)
            playNow(but,cellID)
        }
    }

    private fun playNow(btnselected: Button, currcell: Int) {
        if(activeuser==1){
            btnselected.text="X"
            btnselected.setTextColor(Color.parseColor("#EC0C0C"))
            player1.add(currcell)
            emptycells.add(currcell)
            btnselected.isEnabled=false
            val winner=checkwin()
            if(winner==1){
                Handler().postDelayed(Runnable { reset() },2000)
            }else if(singleuser){
                Handler().postDelayed(Runnable { robot() },500)
            }else{
                activeuser=2
            }
        }else{
            btnselected.text="0"
            btnselected.setTextColor(Color.parseColor("#EC0C0C"))
            activeuser=1
            player2.add(currcell)
            emptycells.add(currcell)
            val winner=checkwin()
            if(winner==1){
                Handler().postDelayed(Runnable { reset() },2000)
            }
        }
    }

    private fun robot() {
        val rand=(1..9).random()
        if(emptycells.contains(rand)){
            robot()
        }else{
            val btnselected=when(rand){
                1->box1
                2->box2
                3->box3
                4->box4
                5->box5
                6->box6
                7->box7
                8->box8
                9->box9
                else->{
                    box1
                }
            }
            emptycells.add(rand)
            btnselected.text="0"
            btnselected.setTextColor(Color.parseColor("#EC0C0C"))
            player2.add(rand)
            btnselected.isEnabled=false
            var winner=checkwin()
            if(winner==1){
                Handler().postDelayed(Runnable { reset() },2000)
            }
        }
    }

    private fun checkwin(): Int {
        if((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
            (player1.contains(3) && player1.contains(5) && player1.contains(7)) ||
            (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(2) && player1.contains(5) && player1.contains(8)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9))){
                player1count+=1
                btndisable()
                disablereset()
                val allert=AlertDialog.Builder(this)
                allert.setTitle("Game Over")
                allert.setMessage("Player 1 wins the Game\n\n"+"Do you to play again")
                allert.setPositiveButton("Yes"){
                    dialog,which->reset()
                }
            allert.setNegativeButton("No"){
                dialog,which->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { allert.show() },2000)
            return 1
        }
        else if((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(3) && player2.contains(5) && player2.contains(7)) ||
            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9))){
            player2count+=1
            btndisable()
            disablereset()
            val allert=AlertDialog.Builder(this)
            allert.setTitle("Game Over")
            allert.setMessage("Player 2 wins the Game\n\n"+"Do you to play again")
            allert.setPositiveButton("Yes"){
                    dialog,which->reset()
            }
            allert.setNegativeButton("No"){
                    dialog,which->
                exitProcess(1)
            }
            Handler().postDelayed(Runnable { allert.show() },2000)
            return 1
        }
        else if(emptycells.contains(1) && emptycells.contains(2) && emptycells.contains(3) && emptycells.contains(4) &&
                emptycells.contains(5) && emptycells.contains(6) && emptycells.contains(7) && emptycells.contains(8) &&
                emptycells.contains(9)){
            val allert=AlertDialog.Builder(this)
            allert.setTitle("Game Draw")
            allert.setMessage("No one wins the game (Game Draw)\n\n"+"Do you to play again")
            allert.setPositiveButton("Yes"){
                    dialog,which->reset()
            }
            allert.setNegativeButton("No"){
                    dialog,which->
                exitProcess(1)
            }
             allert.show()
            return 1
        }
        else{
            return 0
        }

    }

    private fun btndisable() {
        player1.clear()
        player2.clear()
        emptycells.clear()
        activeuser=1
        for(i in 1..9){
            var btnselected : Button ?
            btnselected = when(i){
                1->box1
                2->box2
                3->box3
                4->box4
                5->box5
                6->box6
                7->box7
                8->box8
                9->box9
                else->{
                    box1
                }
            }
            btnselected.isEnabled=true
            btnselected.text=""
            player1Tv.text="Player 1 : $player1count"
            player2Tv.text="Player 2 : $player2count"
        }
    }

    private fun disablereset() {
        resetbtn.isEnabled=false
        Handler().postDelayed(Runnable { resetbtn.isEnabled=true },5000)
    }
}