package com.example.iotassignment

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("channels/2483200/feeds.json?api_key=TY8U8HBWBFWMPIUL&results=1")
    fun fetchData(): Call<ApiResponse>
}