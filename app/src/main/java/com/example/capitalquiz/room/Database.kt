package com.example.capitalquiz.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.capitalquiz.data.entity.Highscore

@androidx.room.Database(entities = [Highscore::class], version = 1)
abstract class Database:RoomDatabase() {
    abstract fun getLeaderBoard() :LeaderBoardDao



    companion object{
        var INSTANCE:Database?=null
        fun databaseAccess(context:Context):Database?{
            if(INSTANCE==null){
                synchronized(Database::class){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,Database::class.java,
                    "highscore.db").createFromAsset("highscore.db").build()
                }
            }
            return INSTANCE
        }

    }
}