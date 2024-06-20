package com.example.tugasakhir.api.retrofit

import android.content.Context
import com.example.tugasakhir.BuildConfig
import com.example.tugasakhir.data.pref.UserPreference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(pref: UserPreference, context: Context): ApiService{
            val loginInterceptor = if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }
            val client = OkHttpClient.Builder()
                .addInterceptor(loginInterceptor)
                .build()
            val baseURL = BuildConfig.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}