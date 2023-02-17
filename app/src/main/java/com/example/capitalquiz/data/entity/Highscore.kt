package com.example.capitalquiz.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "highscore")
data class Highscore(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String,
    var score:Int)


