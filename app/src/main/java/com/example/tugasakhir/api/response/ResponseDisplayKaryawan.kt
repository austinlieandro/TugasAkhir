package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDisplayKaryawan(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("bengkel")
	val karyawan: List<KaryawanItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class KaryawanItem(

	@field:SerializedName("nama_karyawan")
	val namaKaryawan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
