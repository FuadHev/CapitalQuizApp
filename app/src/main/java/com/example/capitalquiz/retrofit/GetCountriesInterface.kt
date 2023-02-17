package com.example.capitalquiz.retrofit

import com.example.capitalquiz.data.entity.Countries
import retrofit2.Call
import retrofit2.http.GET

interface GetCountriesInterface {

    @GET("countries/capital")
    fun getCountries():Call<Countries>


}