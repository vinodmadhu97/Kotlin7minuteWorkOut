package com.example.a7minuteworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.custom_back_dialog_conform_view.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer:CountDownTimer? = null
    private var restProcess = 0

    private var exerciseTimer:CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTime : Long  = 1

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var textToSpeech : TextToSpeech? = null
    private var player : MediaPlayer? = null

    private var exerciseStatusAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(tool_bar_exercise_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tool_bar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        textToSpeech = TextToSpeech(this,this)

        exerciseList = Constant.defaultExerciseList()

        setupRestView()

        setupExerciseStatusRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer!!.cancel()
            restProcess = 0
        }

        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgressBar.progress = 0
        }
        if (textToSpeech != null){
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        if (player != null){
            player!!.stop()
        }
    }

    private fun setRestProgressBar(){
        progressBar.progress = restProcess

        restTimer = object : CountDownTimer(1000,1000){

            override fun onTick(millisUntilFinished: Long) {
                restProcess++
                progressBar.progress = 10 - restProcess
                tvTimer.text = (10 - restProcess).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseStatusAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }

        }.start()

    }

    private fun setExerciseProgressBar(){

        exerciseProgressBar.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer( exerciseTime*1000,1000){

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                exerciseProgressBar.progress = exerciseTime.toInt() - exerciseProgress
                tvExerciseTimer.text = (exerciseTime.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList!!.size -1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)

                    exerciseStatusAdapter!!.notifyDataSetChanged()
                    Toast.makeText(this@ExerciseActivity,"${exerciseList!![currentExercisePosition].getName()}",Toast.LENGTH_SHORT).show()
                    setupRestView()
                }else{
                    var intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }


            }

        }.start()
    }

    private fun setupExerciseView(){
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()
        iv_exerciseImg.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tv_exercise_name.text = exerciseList!![currentExercisePosition].getName()
    }

    private fun setupRestView(){
        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        if(restTimer != null){
            restTimer!!.cancel()
            restProcess = 0
        }
        var text = "up coming exercise is "+exerciseList!![currentExercisePosition+1].getName()

        tv_upcoming_exercise.text = exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
        speakOut(text)
        try {
            player = MediaPlayer.create(this,R.raw.press_start)

            player!!.isLooping = false
            player!!.start()

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = textToSpeech!!.setLanguage(Locale.US)

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this,"Language not supported to your device",Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this,"Initialization Failed",Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut(text:String){
        textToSpeech!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecyclerView(){
        rv_exercise_status.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseStatusAdapter = ExerciseStatusAdapter(this,exerciseList!!)
        rv_exercise_status.adapter = exerciseStatusAdapter
    }

    private fun customDialogForBackButton(){
        val customDialogBox = Dialog(this)
        customDialogBox.setContentView(R.layout.custom_back_dialog_conform_view)

        customDialogBox.btn_yes.setOnClickListener {
            finish()
            customDialogBox.dismiss()
        }
        customDialogBox.btn_no.setOnClickListener {
            customDialogBox.dismiss()
        }
        customDialogBox.show()

    }
}