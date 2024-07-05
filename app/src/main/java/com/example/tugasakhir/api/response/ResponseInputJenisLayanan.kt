package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseInputJenisLayanan(

	@field:SerializedName("jenis_layanan")
	val jenisLayanan: JenisLayanan? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class JenisLayanan(

	@field:SerializedName("harga_layanan")
	val hargaLayanan: Int? = null,

	@field:SerializedName("jenis_layanan")
	val jenisLayanan: List<String?>? = null,

	@field:SerializedName("nama_layanan")
	val namaLayanan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
