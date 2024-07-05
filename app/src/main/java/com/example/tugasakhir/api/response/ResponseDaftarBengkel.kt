package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDaftarBengkel(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("bengkel")
	val bengkel: DaftarBengkel? = null,

	@field:SerializedName("user")
	val user: UserDaftar? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class DaftarBengkel(

	@field:SerializedName("jam_buka")
	val jamBuka: String? = null,

	@field:SerializedName("jam_tutup")
	val jamTutup: String? = null,

	@field:SerializedName("alamat_bengkel")
	val alamatBengkel: String? = null,

	@field:SerializedName("nama_bengkel")
	val namaBengkel: String? = null,

	@field:SerializedName("lokasi_bengkel")
	val lokasiBengkel: String? = null,

	@field:SerializedName("gmaps_bengkel")
	val gmapsBengkel: String? = null,

	@field:SerializedName("users_id")
	val usersId: Int? = null,

	@field:SerializedName("number_bengkel")
	val numberBengkel: String? = null,

	@field:SerializedName("jenis_kendaraan")
	val jenisKendaraan: List<String?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("hari_operasional")
	val hariOperasional: List<String?>? = null
) : Parcelable

@Parcelize
data class UserDaftar(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("user_bengkel")
	val userBengkel: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
