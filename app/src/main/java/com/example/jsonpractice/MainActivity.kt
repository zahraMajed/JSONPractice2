package com.example.jsonpractice

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Tag

class MainActivity : AppCompatActivity() {
    lateinit var tvDate:TextView
    lateinit var tvResult:TextView
    lateinit var edAmount:EditText
    lateinit var btnConvet:Button
    lateinit var spinner: Spinner

    var myDataa : myData? = null

    val Curnlist= listOf("INR","USD","AUD","SAR","CNY","JPY")
    var selectedCur=0//store index of Curnlist to help in calculation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDate=findViewById(R.id.tvDate)
        tvResult=findViewById(R.id.tvResult)
        edAmount=findViewById(R.id.edAmount)
        btnConvet=findViewById(R.id.btnConvert)
        spinner=findViewById(R.id.spinner)

        //what is the role of this if statement ?
        if (spinner != null) {

            //add list as item in drop down menu
            spinner.adapter=ArrayAdapter(this, android.R.layout.simple_spinner_item,Curnlist)

            //to know which currency -index- user chose then save the index in selectedCur
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                //method 1:
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedCur = position
                }
                //method 2:
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }//end if

        btnConvet.setOnClickListener(){
            var numToCon = edAmount.text.toString().toDouble()
            //from here

            getCurrency (onResult = {
                myDataa=it
                when(selectedCur){
                    0 -> resultDisplay( calc(myDataa?.eur?.inr?.toDouble()!!, numToCon) )
                    1 -> resultDisplay( calc(myDataa?.eur?.usd?.toDouble()!!, numToCon))
                    2 -> resultDisplay( calc(myDataa?.eur?.aud?.toDouble()!!, numToCon))
                    3 -> resultDisplay( calc(myDataa?.eur?.sar?.toDouble()!!, numToCon))
                    4 -> resultDisplay( calc(myDataa?.eur?.cny?.toDouble()!!, numToCon))
                    5 -> resultDisplay( calc(myDataa?.eur?.jpy?.toDouble()!!, numToCon))
                }
            })//end getCurrency call

        } //end btnListener

    }// end onCreate()


    fun getCurrency(onResult: (myData?) -> Unit){
        val apiInterface=APIclint().getClient()?.create(APIInterface::class.java)

        apiInterface?.getDate()?.enqueue(object : Callback<myData?> {
            override fun onResponse(call: Call<myData?>, response: Response<myData?>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<myData?>, t: Throwable) {
                onResult(null)
                Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun resultDisplay(num: Double){
        tvResult.text= "Result: $num"
        tvResult.isVisible=true
        tvDate.text = "Date: ${myDataa?.date}"
    }//end display

    fun calc(i:Double, numTo:Double):Double{
        var convertedNum= 0.0
        if (i != null){
            convertedNum=(numTo*i)
        }
        return convertedNum
    }//end calc


}//end class

