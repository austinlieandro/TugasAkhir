package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseJamOperasionalBengkel(

	@field:SerializedName("jamOperasional")
	val jamOperasional: List<InputJamOperasional?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class InputJamOperasional(

	@field:SerializedName("jam_operasional")
	val jamOperasional: String? = null,

	@field:SerializedName("slot")
	val slot: Int? = null,

	@field:SerializedName("hari_operasional")
	val hariOperasional: String? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
