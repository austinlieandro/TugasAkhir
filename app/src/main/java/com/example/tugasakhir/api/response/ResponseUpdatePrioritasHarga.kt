package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUpdatePrioritasHarga(

	@field:SerializedName("prioritas")
	val prioritas: UpdatePrioritasHarga? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class UpdatePrioritasHarga(

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("bobot_nilai")
	val bobotNilai: Int? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
