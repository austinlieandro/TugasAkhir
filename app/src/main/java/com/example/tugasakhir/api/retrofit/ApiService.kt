package com.example.tugasakhir.api.retrofit

import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.response.ResponseLogin
import com.example.tugasakhir.api.response.ResponseRegister
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService{
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("username") username:String,
        @Field("password") password: String,
        @Field("phone") phone: String,
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") passowrd: String,
    ): ResponseLogin

    @GET("bengkel")
    suspend fun displayBengkel(): ResponseDisplayBengkel

    @GET("bengkel/{bengkelId}")
    suspend fun detailBengkel(
        @Path("bengkelId") bengkelId: Int
    ): ResponseDetailBengkel

}