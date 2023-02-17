package com.example.capitalquiz.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import com.example.capitalquiz.R
import com.example.capitalquiz.data.entity.Highscore
import com.example.capitalquiz.databinding.FragmentLoginBinding
import com.example.capitalquiz.room.Database
import com.example.capitalquiz.room.LeaderBoardDao
import com.example.capitalquiz.ui.adapter.LeaderBoardAdapter
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private var _binding:FragmentLoginBinding?=null
    private val binding get() =_binding!!
    private lateinit var db: Database
    private lateinit var kdo: LeaderBoardDao
    private lateinit var leaderBoardList:ArrayList<Highscore>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        val view=binding.root
        binding.start.background.alpha=100
        db= Database.databaseAccess(requireContext())!!
        kdo=db.getLeaderBoard()
        leaderBoardList=ArrayList()
        binding.leaderBoard.setOnClickListener {

            val job= CoroutineScope(Dispatchers.Main).launch {
                val income=kdo.allScore()
                for (i in income){
                    leaderBoardList.add(i)
                }
                if (leaderBoardList.isEmpty()){
                    Toast.makeText(requireContext(), "Leaderboard no exist", Toast.LENGTH_SHORT).show()
                }
                else{
                    Navigation.findNavController(it).navigate(R.id.goToLeaderBoard)
                }
            }


        }



        binding.textInputEditText.doOnTextChanged { text, start, before, count ->


             if (text!!.length > 9){
                 binding.textInputLayout.error="No more"
                 binding.start.isClickable = false
                 binding.start.background.alpha=100

            }
            else if (text.length in 1..9){
                 binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
                 binding.textInputLayout.setEndIconDrawable(R.drawable.true_chech_mark)

                 binding.textInputLayout.error=null
                 binding.start.isClickable = true
                 binding.start.background.alpha=255
                 start()

            }else{
                 binding.textInputLayout.endIconMode=TextInputLayout.END_ICON_NONE
                 binding.start.isClickable = false
                 binding.start.background.alpha=100

             }

        }






        return view
    }
    fun start(){
        binding.start.setOnClickListener {
            if (binding.start.isClickable){
                val user=binding.textInputEditText.text.toString()
               val goTo=LoginFragmentDirections.loginToquiz(user)
                Navigation.findNavController(it).navigate(goTo)
            }
        }

    }



}