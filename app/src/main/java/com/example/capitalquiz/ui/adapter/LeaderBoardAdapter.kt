package com.example.capitalquiz.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capitalquiz.data.entity.Highscore
import com.example.capitalquiz.databinding.LeaderboardViewBinding

class LeaderBoardAdapter(private val context:Context,private val leaderBoardList:List<Highscore>):RecyclerView.Adapter<LeaderBoardAdapter.CardViewHolder>() {

    inner class CardViewHolder(view:LeaderboardViewBinding ):RecyclerView.ViewHolder(view.root){
        val view:LeaderboardViewBinding
        init {
            this.view=view
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater=LayoutInflater.from(context)
        val view:LeaderboardViewBinding= LeaderboardViewBinding.inflate(layoutInflater,parent,false)
        return CardViewHolder(view)

    }

    override fun getItemCount(): Int {
        return leaderBoardList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val scores=leaderBoardList[position]
         val b=holder.view

        b.noId.text= (leaderBoardList.indexOf(scores)+1).toString()
        b.userName.text=scores.name
        b.textViewscore.text=scores.score.toString()


    }
}