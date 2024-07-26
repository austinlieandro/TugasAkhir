package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDisplayPrioritas(

	@field:SerializedName("prioritas")
	val prioritas: List<PrioritasItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class PrioritasItem(

	@field:SerializedName("bobot_urgensi")
	val bobotUrgensi: Int? = null,

	@field:SerializedName("jenis_kerusakan")
	val jenisKerusakan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jenis_kendaraan")
	val jenisKendaraan: String? = null,

	@field:SerializedName("bobot_nilai")
	val bobotNilai: Int? = null,

	@field:SerializedName("bobot_estimasi")
	val bobotEstimasi: Int? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
