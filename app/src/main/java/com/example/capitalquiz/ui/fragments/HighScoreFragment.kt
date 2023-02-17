package com.example.capitalquiz.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.capitalquiz.R
import com.example.capitalquiz.data.entity.Highscore
import com.example.capitalquiz.databinding.FragmentHighScoreBinding
import com.example.capitalquiz.room.Database
import com.example.capitalquiz.room.LeaderBoardDao
import com.example.capitalquiz.ui.adapter.LeaderBoardAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HighScoreFragment : Fragment() {
    private var _binding:FragmentHighScoreBinding?=null
    private val binding get()=_binding!!
    private lateinit var adapter:LeaderBoardAdapter
    private lateinit var db:Database
    private lateinit var kdo:LeaderBoardDao
    private lateinit var leaderBoardList:ArrayList<Highscore>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHighScoreBinding.inflate(inflater,container,false)
        val view=binding.root
        leaderBoardList=ArrayList()



        return view
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db= Database.databaseAccess(requireContext())!!
        kdo=db.getLeaderBoard()

        binding.button.setOnClickListener {
            val job= CoroutineScope(Dispatchers.Main).launch {
                kdo.deleteAllScore()
                adapter = LeaderBoardAdapter(requireContext(), emptyList())
                binding.rv.adapter = adapter
            }
        }

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        getLeaderboard()


        val callback=object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_highScoreFragment_to_loginFragment2)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)


    }



    @SuppressLint("SetTextI18n")
    fun getLeaderboard(){
        val job= CoroutineScope(Dispatchers.Main).launch {
            val income=kdo.allScore()
            for (i in income){
                leaderBoardList.add(i)
            }
            leaderBoardList.sortBy {
                it.score
            }
            leaderBoardList.reverse()

            adapter= LeaderBoardAdapter(requireContext(),leaderBoardList)


            if (leaderBoardList.size>=3){
                binding.first.text=leaderBoardList[0].name
                binding.second.text=leaderBoardList[1].name
                binding.third.text=leaderBoardList[2].name
            }else if (leaderBoardList.size==2){
                    binding.first.text=leaderBoardList[0].name
                    binding.second.text=leaderBoardList[1].name
                    binding.third.text= "No data"


            }else{
                binding.first.text=leaderBoardList[0].name
                binding.second.text="No data"
                binding.third.text= "No data"


            }


            binding.rv.adapter=adapter


        }


    }
}