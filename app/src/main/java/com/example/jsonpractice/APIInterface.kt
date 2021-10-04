package com.example.jsonpractice

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {

    //here we will write a get request
    // to make a get request :
    // we have to make function that return a call class of retrofit
    //add @get and the endpoint (last part in url)
    @GET("eur.json")
    fun getDate ():Call<myData>?
}