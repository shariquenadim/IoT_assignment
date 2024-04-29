package com.example.iotassignment

import android.content.Context
import android.provider.ContactsContract.Data
import android.widget.Toast
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class ApiCall {
    fun fetchData(context: Context, callback: (ApiResponse) -> Unit){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.thingspeak.com/").addConverterFactory(
            GsonConverterFactory.create()).build()

        // Create an ApiService instance from the Retrofit instance.
        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<ApiResponse> = service.fetchData()

        // Use the enqueue() method of the Call object to
        // make an asynchronous API request.
        call.enqueue(object : Callback<ApiResponse> {
            // This is an anonymous inner class that implements the Callback interface.

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if(response.isSuccessful){
                    // If the response is successful, parse the
                    // response body to a DataModel object.
                    val data: ApiResponse = response.body() as ApiResponse

                    // Call the callback function with the DataModel
                    // object as a parameter.
                    callback(data)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}