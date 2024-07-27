package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseInputPriortiasHarga(

	@field:SerializedName("prioritas")
	val prioritas: List<InputPrioritasHarga?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class InputPrioritasHarga(

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("bobot_nilai")
	val bobotNilai: Int? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
