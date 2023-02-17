package com.example.capitalquiz.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.capitalquiz.data.entity.Highscore
import com.example.capitalquiz.retrofit.GetCountriesInterface

@Dao
interface LeaderBoardDao {
    @Query(" SELECT * FROM highscore ")
    suspend fun allScore():List<Highscore>

    @Query("DELETE FROM highscore")
    suspend fun deleteAllScore()

    @Insert
    suspend fun newScore(score:Highscore)
}