package com.example.capitalquiz.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.capitalquiz.R
import com.example.capitalquiz.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private var _binding:FragmentResultBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding= FragmentResultBinding.inflate(inflater,container,false)
        val view=binding.root


        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle:ResultFragmentArgs by navArgs()
        var user=bundle.score

        binding.score.text=" $user "
        binding.again.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_loginFragment)
        }

        binding.leaderboard.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_resultFragment_to_highScoreFragment)


        }


    }
}