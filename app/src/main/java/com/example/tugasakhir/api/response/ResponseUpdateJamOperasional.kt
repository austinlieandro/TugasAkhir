package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUpdateJamOperasional(

	@field:SerializedName("jamOperasional")
	val jamOperasional: JamOperasionalUpdate? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class JamOperasionalUpdate(

	@field:SerializedName("jam_operasional")
	val jamOperasional: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slot")
	val slot: Int? = null,

	@field:SerializedName("hari_operasional")
	val hariOperasional: String? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
