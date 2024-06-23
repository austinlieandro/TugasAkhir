package com.example.tugasakhir.api.retrofit

import com.example.tugasakhir.api.response.ResponseDaftarBengkel
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.response.ResponseLogin
import com.example.tugasakhir.api.response.ResponseProfile
import com.example.tugasakhir.api.response.ResponseRegister
import com.example.tugasakhir.api.response.ResponseReservasiBengkel
import com.example.tugasakhir.api.response.ResponseRiwayatReservasi
import okhttp3.Request
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.HashMap

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

    @FormUrlEncoded
    @POST("userReservasi")
    suspend fun reservasiBengkel(
        @Field("tanggal_reservasi") tanggal_reservasi: String,
        @Field("jam_reservasi") jam_reservasi:String,
        @Field("jeniskendala_reservasi") jeniskendala_reservasi: String,
        @Field("detail_reservasi") detail_reservasi: String,
        @Field("kendaraan_reservasi") kendaraan_reservasi:String,
        @Field("bengkels_id") bengkels_id: Int,
        @Field("users_id") users_id: Int
    ): ResponseReservasiBengkel

    @GET("showReservasiUser/{usersId}")
    suspend fun riwayatReservasi(
        @Path("usersId") usersId: Int
    ): ResponseRiwayatReservasi

    @GET("profile/{usersId}")
    suspend fun profile(
        @Path("usersId") usersId: Int
    ): ResponseProfile

    @POST("daftarBengkel")
    suspend fun daftarBengkel(
        @Body request: HashMap<String, Any>
    ): ResponseDaftarBengkel
}