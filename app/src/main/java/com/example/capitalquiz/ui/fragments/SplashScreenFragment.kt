package com.example.capitalquiz.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.capitalquiz.R
import com.example.capitalquiz.databinding.FragmentSplashScreenBinding


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private var _binding:FragmentSplashScreenBinding?=null
    private val binding get() = _binding!!
    private val splashscreen=1000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSplashScreenBinding.inflate(inflater,container,false)
        val view=binding.root




        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.myLooper()!!).postDelayed({

            findNavController().navigate(R.id.splashTologin)


        },splashscreen.toLong())




    }


}