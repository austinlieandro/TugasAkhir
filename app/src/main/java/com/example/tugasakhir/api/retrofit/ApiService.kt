package com.example.tugasakhir.api.retrofit

import com.example.tugasakhir.api.response.ResponseAsignKaryawan
import com.example.tugasakhir.api.response.ResponseDaftarBengkel
import com.example.tugasakhir.api.response.ResponseDeleteKaryawan
import com.example.tugasakhir.api.response.ResponseDeleteKendaraan
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDetailKendaraan
import com.example.tugasakhir.api.response.ResponseDetailReservasiBengkel
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.response.ResponseDisplayKaryawan
import com.example.tugasakhir.api.response.ResponseDisplayKendaraan
import com.example.tugasakhir.api.response.ResponseDisplayReservasiBengkel
import com.example.tugasakhir.api.response.ResponseFavoritBengkel
import com.example.tugasakhir.api.response.ResponseInputKaryawan
import com.example.tugasakhir.api.response.ResponseInputKendaraan
import com.example.tugasakhir.api.response.ResponseJamOperasionalBengkel
import com.example.tugasakhir.api.response.ResponseLogin
import com.example.tugasakhir.api.response.ResponseProfile
import com.example.tugasakhir.api.response.ResponseRegister
import com.example.tugasakhir.api.response.ResponseReservasiBengkel
import com.example.tugasakhir.api.response.ResponseRiwayatReservasi
import com.example.tugasakhir.api.response.ResponseTogleFavorit
import com.example.tugasakhir.api.response.ResponseUpdateBengkel
import com.example.tugasakhir.api.response.ResponseUpdateJamOperasional
import com.example.tugasakhir.api.response.ResponseUpdateKaryawan
import com.example.tugasakhir.api.response.ResponseUpdateKendaraan
import com.example.tugasakhir.api.response.ResponseUpdateProfile
import retrofit2.http.Body
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

    @GET("bengkel/{usersId}/{bengkelId}")
    suspend fun detailBengkel(
        @Path("usersId") usersId: Int,
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
        @Field("users_id") users_id: Int,
        @Field("kendaraan_id") kendaraan_id: Int,
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

    @POST("updateBengkel/{usersId}/{id}")
    suspend fun updateBengkel(
        @Path("usersId") bengkelId: Int,
        @Path("id") id: Int,
        @Body request: HashMap<Any, Any>
    ): ResponseUpdateBengkel

    @POST("jamOperasional")
    suspend fun daftarJamOperasional(
        @Body request: HashMap<String, Any>
    ): ResponseJamOperasionalBengkel

    @FormUrlEncoded
    @POST("daftarKaryawan")
    suspend fun inputKaryawanbengkel(
        @Field("nama_karyawan") nama_karyawan: String,
        @Field("bengkels_id") bengkels_id: Int
    ): ResponseInputKaryawan

    @GET("karyawan/{id}")
    suspend fun displayKaryawan(
        @Path("id") id: Int
    ): ResponseDisplayKaryawan

    @FormUrlEncoded
    @POST("updateKaryawan/{bengkelId}/{id}")
    suspend fun updateKaryawan(
        @Path("bengkelId") bengkelId: Int,
        @Path("id") id: Int,
        @Field("nama_karyawan") nama_karyawan: String
    ): ResponseUpdateKaryawan

    @POST("deleteKaryawan/{bengkelId}/{id}")
    suspend fun deleteKaryawan(
        @Path("bengkelId") bengkelId: Int,
        @Path("id") id: Int,
    ): ResponseDeleteKaryawan

    @GET("showReservasiBengkel/{bengkelId}")
    suspend fun displayReservasiBengkel(
        @Path("bengkelId") bengkelId: Int
    ): ResponseDisplayReservasiBengkel

    @GET("detailReservasiBengkel/{id}")
    suspend fun detailReservasiBengkel(
        @Path("id") id: Int
    ): ResponseDetailReservasiBengkel

    @FormUrlEncoded
    @POST("assignKaryawan")
    suspend fun assignKaryawan(
        @Field("id") id: Int,
        @Field("karyawan_id") karyawan_id: Int,
        @Field("status_reservasi") status_reservasi: String
    ): ResponseAsignKaryawan

    @GET("kendaraan/{id}")
    suspend fun displayKendaraan(
        @Path("id") id: Int
    ): ResponseDisplayKendaraan

    @FormUrlEncoded
    @POST("kendaraan")
    suspend fun inputKendaraan(
        @Field("jenis_kendaraan") jenis_kendaraan: String,
        @Field("plat_kendaraan") plat_kendaraan: String,
        @Field("merek_kendaraan") merek_kendaraan: String,
        @Field("users_id") users_id: Int
    ): ResponseInputKendaraan

    @GET("detailKendaraan/{usersId}/{kendaraan_id}")
    suspend fun detailKendaraan(
        @Path("usersId") usersId: Int,
        @Path("kendaraan_id") kendaraan_id: Int,
    ): ResponseDetailKendaraan

    @FormUrlEncoded
    @POST("kendaraan/{usersId}/{kendaraan_id}")
    suspend fun updateKendaraan(
        @Path("usersId") usersId: Int,
        @Path("kendaraan_id") kendaraan_id: Int,
        @Field("plat_kendaraan") plat_kendaraan: String,
        @Field("merek_kendaraan") merek_kendaraan: String
    ): ResponseUpdateKendaraan

    @POST("deleteKendaraan/{usersId}/{kendaraan_id}")
    suspend fun deleteKendaraan(
        @Path("usersId") usersId: Int,
        @Path("kendaraan_id") kendaraan_id: Int,
    ): ResponseDeleteKendaraan

    @GET("displayUserFavorit/{id}")
    suspend fun favoriteBengkel(
        @Path("id") id: Int
    ): ResponseFavoritBengkel

    @FormUrlEncoded
    @POST("updateProfile/{id}")
    suspend fun updateProfile(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
    ): ResponseUpdateProfile

    @FormUrlEncoded
    @POST("togleFavorite")
    suspend fun togleFavorite(
        @Field("users_id") users_id: Int,
        @Field("bengkels_id") bengkels_id: Int
    ): ResponseTogleFavorit

    @FormUrlEncoded
    @POST("updateOperasional/{bengkelId}/{id}")
    suspend fun updateJamOperasional(
        @Path("bengkelId") bengkelId: Int,
        @Path("id") id: Int,
        @Field("jam_operasional") jam_operasional: String,
        @Field("slot") slot: Int
    ): ResponseUpdateJamOperasional
}