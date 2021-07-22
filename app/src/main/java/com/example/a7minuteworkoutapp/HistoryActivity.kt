package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        toolbar_history_activity.title = "History"

        getAllCompletedDates()

    }

    private fun getAllCompletedDates(){
        val dbHandler = SqliteDbHelper(this,null)

        val completedDateList = dbHandler.getAllCompletedDateList()

        if (completedDateList.size > 0){
            tv_exercise_completed.visibility = View.VISIBLE
            rv_history_list.visibility = View.VISIBLE
            tv_data_no_available.visibility = View.GONE

            rv_history_list.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this,completedDateList)
            rv_history_list.adapter = historyAdapter
        }else{
            tv_exercise_completed.visibility = View.GONE
            rv_history_list.visibility = View.GONE
            tv_data_no_available.visibility = View.VISIBLE
        }
    }
}