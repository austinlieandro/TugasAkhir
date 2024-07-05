package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseInputMerekKendaraan(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("merek_kendaraan")
	val merekKendaraan: InputMerekKendaraan? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class InputMerekKendaraan(

	@field:SerializedName("users_id")
	val usersId: Int? = null,

	@field:SerializedName("jenis_kendaraan")
	val jenisKendaraan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("merek_kendaraan")
	val merekKendaraan: String? = null
) : Parcelable
