package com.example.a7minuteworkoutapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteDbHelper(context: Context,factory:SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context,
    DATABASE_NAME,factory, DATABSE_VERSION) {
    companion object{
        private val DATABSE_VERSION = 1
        private val DATABASE_NAME = "sevenMinuteWorkout.db"
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completedDate"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val exerciseTable = ("CREATE TABLE "+ TABLE_HISTORY + "("+ COLUMN_ID+" INTEGER PRIMARY KEY,"
                + COLUMN_COMPLETED_DATE + " TEXT)")
        db?.execSQL(exerciseTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS "+ TABLE_HISTORY)
        onCreate(db)
    }

    fun addDate(date:String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE,date)
        db.insert(TABLE_HISTORY,null,values)
        db.close()
    }

    fun getAllCompletedDateList() : ArrayList<String>{
        val list = ArrayList<String>()
        val db = readableDatabase

        var cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY",null)

        while (cursor.moveToNext()){
            val dateValue = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            list.add(dateValue)
        }

        cursor.close()

        return list
    }
}