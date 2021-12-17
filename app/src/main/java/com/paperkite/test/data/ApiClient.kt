package com.paperkite.test.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var apiInterface:ApiInterface? = null
    private const val BASE_URL  = "https://api.thecatapi.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun buildService():ApiInterface {
        if (apiInterface == null) {
            synchronized(ApiInterface::class.java)
            {
                if (apiInterface == null) {
                    val builder: Retrofit.Builder = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                    val retrofit2 = builder.build()
                    apiInterface = retrofit2.create(ApiInterface::class.java)
                }
            }
        }
        return apiInterface as ApiInterface
    }


    private fun interceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

}