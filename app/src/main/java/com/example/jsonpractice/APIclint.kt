package com.example.jsonpractice

import androidx.core.graphics.rotationMatrix
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIclint {

    var retrofitBuilder:Retrofit?= null

    //this method will return retrofit
    fun getClient(): Retrofit? {
        //what do next three line mean?
        val interceptor= HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY
        val client=OkHttpClient.Builder().addInterceptor(interceptor).build()

        //build retrofit
        //do not use full url (full but except last part -endpoint-)
        retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofitBuilder
    }
}