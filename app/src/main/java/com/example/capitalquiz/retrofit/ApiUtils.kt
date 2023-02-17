package com.example.capitalquiz.retrofit

class ApiUtils {
    companion object{
        val BASE_URL="https://countriesnow.space/api/v0.1/"
        fun getPlanetsInterface():GetCountriesInterface{
            return RetrofitClient.getClient(BASE_URL)
                .create(GetCountriesInterface::class.java)


        }

    }
}