package com.paperkite.test.data

import com.paperkite.test.model.Cat
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
        @GET("v1/images/search?format=json&limit=10")
        fun getCat(): Call<List<Cat>>

}