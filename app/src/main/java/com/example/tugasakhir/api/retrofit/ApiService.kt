package com.example.tugasakhir.api.retrofit

import com.example.tugasakhir.api.response.ResponseAsignKaryawan
import com.example.tugasakhir.api.response.ResponseDaftarBengkel
import com.example.tugasakhir.api.response.ResponseDeleteKaryawan
import com.example.tugasakhir.api.response.ResponseDeleteKendaraan
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDetailJenisLayanan
import com.example.tugasakhir.api.response.ResponseDetailKendaraan
import com.example.tugasakhir.api.response.ResponseDetailReservasiBengkel
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.response.ResponseDisplayJenisService
import com.example.tugasakhir.api.response.ResponseDisplayKaryawan
import com.example.tugasakhir.api.response.ResponseDisplayKendaraan
import com.example.tugasakhir.api.response.ResponseDisplayMerekKendaraan
import com.example.tugasakhir.api.response.ResponseDisplayPrioritas
import com.example.tugasakhir.api.response.ResponseDisplayPrioritasHarga
import com.example.tugasakhir.api.response.ResponseDisplayReservasiBengkel
import com.example.tugasakhir.api.response.ResponseFavoritBengkel
import com.example.tugasakhir.api.response.ResponseInputJamOperasionalSatuan
import com.example.tugasakhir.api.response.ResponseInputJenisLayanan
import com.example.tugasakhir.api.response.ResponseInputJenisService
import com.example.tugasakhir.api.response.ResponseInputKaryawan
import com.example.tugasakhir.api.response.ResponseInputKendaraan
import com.example.tugasakhir.api.response.ResponseInputMerekKendaraan
import com.example.tugasakhir.api.response.ResponseInputPrioritasSatuan
import com.example.tugasakhir.api.response.ResponseInputPriortiasHarga
import com.example.tugasakhir.api.response.ResponseJamOperasionalBengkel
import com.example.tugasakhir.api.response.ResponseJenisLayanan
import com.example.tugasakhir.api.response.ResponseLogin
import com.example.tugasakhir.api.response.ResponseProfile
import com.example.tugasakhir.api.response.ResponseRegister
import com.example.tugasakhir.api.response.ResponseReservasiBengkel
import com.example.tugasakhir.api.response.ResponseRiwayatReservasi
import com.example.tugasakhir.api.response.ResponseTogleFavorit
import com.example.tugasakhir.api.response.ResponseUpdateBengkel
import com.example.tugasakhir.api.response.ResponseUpdateJamOperasional
import com.example.tugasakhir.api.response.ResponseUpdateJenisLayanan
import com.example.tugasakhir.api.response.ResponseUpdateJenisService
import com.example.tugasakhir.api.response.ResponseUpdateKaryawan
import com.example.tugasakhir.api.response.ResponseUpdateKendaraan
import com.example.tugasakhir.api.response.ResponseUpdateMerekKendaraan
import com.example.tugasakhir.api.response.ResponseUpdatePrioritas
import com.example.tugasakhir.api.response.ResponseUpdatePrioritasHarga
import com.example.tugasakhir.api.response.ResponseUpdateProfile
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Path("bengkelId") bengkelId: Int,
        @Query("tanggal_reservasi") tanggalReservasi: String,
        @Query("jam_reservasi") jamReservasi: String
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
        @Field("users_id") users_id: Int,
        @Field("merek_kendaraan_id") merek_kendaraan_id: Int,
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
        @Field("merek_kendaraan_id") merek_kendaraan_id: Int
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

    @POST("inputJenisLayanan")
    suspend fun inputJenisLayanan(
        @Body request: HashMap<String, Any>
    ): ResponseInputJenisLayanan

    @GET("jenisLayanan/{bengkelId}")
    suspend fun getJenisLayanan(
        @Path("bengkelId") bengkelId: Int
    ): ResponseJenisLayanan

    @POST("updateJenisLayanan/{bengkelId}/{id}")
    suspend fun updateJenisLayanan(
        @Path("bengkelId") bengkelId: Int,
        @Path("id") id: Int,
        @Body request: HashMap<Any, Any>
    ): ResponseUpdateJenisLayanan

    @GET("detailJenisLayanan/{id}")
    suspend fun detailJenisLayanan(
        @Path("id") id: Int
    ): ResponseDetailJenisLayanan

    @FormUrlEncoded
    @POST("inputMerekKendaraan")
    suspend fun inputMerekKendaraan(
        @Field("jenis_kendaraan") jenis_kendaraan:String,
        @Field("merek_kendaraan") merek_kendaraan:String,
        @Field("users_id") users_id:Int,
    ): ResponseInputMerekKendaraan

    @GET("displayMerekKendaraan")
    suspend fun displayMerekKendaraan():ResponseDisplayMerekKendaraan

    @FormUrlEncoded
    @POST("updateMerekKendaraan/{usersId}/{merekKendaraanId}")
    suspend fun updateMerekKendaraan(
        @Path("usersId") usersId: Int,
        @Path("merekKendaraanId") merekKendaraanId: Int,
        @Field("merek_kendaraan") merek_kendaraan: String,
    ): ResponseUpdateMerekKendaraan

    @FormUrlEncoded
    @POST("inputJenisService")
    suspend fun inputJenisService(
        @Field("nama_service") nama_service: String,
        @Field("users_id") usersId: Int,
    ): ResponseInputJenisService

    @GET("displayJenisService")
    suspend fun displayJenisService(): ResponseDisplayJenisService

    @FormUrlEncoded
    @POST("updateJenisService/{usersId}/{serviceId}")
    suspend fun updateJenisService(
        @Path("usersId") usersId: Int,
        @Path("serviceId") serviceId: Int,
        @Field("nama_service") nama_service: String,
    ): ResponseUpdateJenisService

    @GET("displayPrioritas/{bengkelsId}")
    suspend fun displayPrioritas(
        @Path("bengkelsId") bengkelsId: Int
    ): ResponseDisplayPrioritas

    @GET("displayPrioritasHarga/{bengkelsId}")
    suspend fun displayPrioritasHarga(
        @Path("bengkelsId") bengkelsId: Int
    ): ResponseDisplayPrioritasHarga

    @FormUrlEncoded
    @POST("inputJamOperasional")
    suspend fun inputJamOperasionalSatuan(
        @Field("jam_operasional") jam_operasional: String,
        @Field("hari_operasional") hari_operasional: String,
        @Field("slot") slot: Int,
        @Field("bengkels_id") bengkels_id: Int,
    ): ResponseInputJamOperasionalSatuan

    @POST("inputPrioritasHarga")
    suspend fun inputPrioritasHarga(
        @Body request: HashMap<Any, Any>
    ): ResponseInputPriortiasHarga

    @FormUrlEncoded
    @POST("editPrioritasHarga/{bengkelsId}/{id}")
    suspend fun updatePrioritasHarga(
        @Path("bengkelsId") bengkelsId: Int,
        @Path("id") id: Int,
        @Field("harga") harga: Int,
        @Field("bobot_nilai") bobot_nilai: Int,
    ): ResponseUpdatePrioritasHarga

    @FormUrlEncoded
    @POST("inputPrioritasSatu")
    suspend fun inputPrioritasSatuan(
        @Field("jenis_kendaraan") jenis_kendaraan: String,
        @Field("jenis_kerusakan") jenis_kerusakan: String,
        @Field("bobot_nilai") bobot_nilai: Int,
        @Field("bobot_estimasi") bobot_estimasi: Int,
        @Field("bobot_urgensi") bobot_urgensi: Int,
        @Field("bengkels_id") bengkels_id: Int,
    ): ResponseInputPrioritasSatuan

    @FormUrlEncoded
    @POST("editPrioritas/{bengkelsId}/{id}")
    suspend fun updatePrioritas(
        @Path("bengkelsId") bengkelsId: Int,
        @Path("id") id: Int,
        @Field("bobot_nilai") bobot_nilai: Int,
        @Field("bobot_estimasi") bobot_estimasi: Int,
        @Field("bobot_urgensi") bobot_urgensi: Int,
    ): ResponseUpdatePrioritas
}