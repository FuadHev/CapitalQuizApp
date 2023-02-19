package com.example.capitalquiz.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.capitalquiz.R
import com.example.capitalquiz.data.entity.Countries
import com.example.capitalquiz.data.entity.Data
import com.example.capitalquiz.data.entity.Highscore
import com.example.capitalquiz.databinding.FragmentQuizBinding
import com.example.capitalquiz.retrofit.ApiUtils
import com.example.capitalquiz.retrofit.GetCountriesInterface
import com.example.capitalquiz.room.Database
import com.example.capitalquiz.room.LeaderBoardDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates
import kotlin.random.Random


class QuizFragment : Fragment() {
    private  var _binding:FragmentQuizBinding?=null
    private  val  binding get() = _binding!!
    private var trueAnswer=0
    private var wrongAnswer=0
    private lateinit var truequestions:Data
    private lateinit var kdi:GetCountriesInterface
    private lateinit var wrongQuestionList:ArrayList<Data>
    private lateinit var allQuestion:HashSet<String>
    private lateinit var countriesList:ArrayList<Data>
    private lateinit var user:String
    private lateinit var db:Database
    private lateinit var kdo:LeaderBoardDao
    private var check by Delegates.notNull<Boolean>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentQuizBinding.inflate(inflater,container,false)
        val view=binding.root
        countriesList= ArrayList()
        db=Database.databaseAccess(requireContext())!!
        kdo=db.getLeaderBoard()
        truequestions=Data("","","","")
        kdi=ApiUtils.getPlanetsInterface()

        return view

    }
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCountries()

        val bundle:QuizFragmentArgs by navArgs()
        user=bundle.user
        countTimer()
        binding.buttonA.setOnClickListener {
            checkAnswer(binding.buttonA)
        }

        binding.buttonB.setOnClickListener {
            checkAnswer(binding.buttonB)
        }


        binding.buttonC.setOnClickListener {
            checkAnswer(binding.buttonC)
        }

        binding.buttonD.setOnClickListener {
            checkAnswer(binding.buttonD)

        }





    }
    fun getCountries(){

        val countriesList= arrayListOf<Data>()
        kdi.getCountries().enqueue(object :Callback<Countries>{
            override fun onResponse(call: Call<Countries>?, response: Response<Countries>?) {
                if (response != null){
                    val list=response.body().data
                    for(i in list){
                        countriesList.add(i)
                    }
                     addQuestion(countriesList)
                }
            }

            override fun onFailure(call: Call<Countries>?, t: Throwable?) {
                TODO("Not yet implemented")
            }


        })

    }

    @SuppressLint("SetTextI18n")
    fun addQuestion(countriesList:ArrayList<Data>){
        val randomValue=(1..240).random()
        val wrongQuestionRandom=List(3){
            Random.nextInt(1,240)
        }
        btnColor()
        allQuestion= hashSetOf()
        wrongQuestionList=ArrayList()
        for (i in wrongQuestionRandom){
            if(countriesList[i].capital==""){
                allQuestion.add(countriesList[i+1].capital)
            }else{
                allQuestion.add(countriesList[i].capital)
            }
        }
        binding.quiztextView.text="Which's ${truequestions.name}'s capital?"

        if (truequestions.capital==""){
            truequestions=countriesList[randomValue+1]
        }else{
            truequestions=countriesList[randomValue]
        }

        allQuestion.add(truequestions.capital)

        binding.buttonA.text=allQuestion.elementAt(0)
        binding.buttonB.text=allQuestion.elementAt(1)
        binding.buttonC.text=allQuestion.elementAt(2)
        binding.buttonD.text=allQuestion.elementAt(3)

    }
    fun checkAnswer(button: Button){

        val buttontext=button.text.toString()
        val trueanswer=truequestions.capital
        if (buttontext==trueanswer){
            ++trueAnswer
            check=true

        }
        else{
            ++wrongAnswer
            check=false
        }

        if (check==true){
            button.setBackgroundResource(R.drawable.truebutton_bg)
        }else{
            button.setBackgroundResource(R.drawable.wrongbtn_bg)
        }
        binding.buttonA.isClickable=false
        binding.buttonB.isClickable=false
        binding.buttonC.isClickable=false
        binding.buttonD.isClickable=false
        getCountries()


    }

    fun countTimer(){
        val count=object:CountDownTimer(10000,1000){
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.countTimer.text="Time : ${millisUntilFinished/1000} s"
            }

            override fun onFinish() {
                val goTo=QuizFragmentDirections.actionQuizFragmentToResultFragment(trueAnswer)
                findNavController().navigate(goTo)
                addScore()

            }

        }
        count.start()
    }
    fun addScore(){
        val job= CoroutineScope(Dispatchers.Main).launch{
            val newScore=Highscore(0,user,trueAnswer)
            kdo.newScore(newScore)
        }
    }


    fun btnColor(){
        binding.buttonA.setBackgroundResource(R.drawable.button_bg)
        binding.buttonB.setBackgroundResource(R.drawable.button_bg)
        binding.buttonC.setBackgroundResource(R.drawable.button_bg)
        binding.buttonD.setBackgroundResource(R.drawable.button_bg)
        binding.buttonA.isClickable=true
        binding.buttonB.isClickable=true
        binding.buttonC.isClickable=true
        binding.buttonD.isClickable=true
    }


}


