package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDisplayMerekKendaraan(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("merek_kendaraan")
	val merekKendaraan: List<MerekKendaraanItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class MerekKendaraanItem(

	@field:SerializedName("users_id")
	val usersId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jenis_kendaraan")
	val jenisKendaraan: String? = null,

	@field:SerializedName("merek_kendaraan")
	val merekKendaraan: String? = null
) : Parcelable