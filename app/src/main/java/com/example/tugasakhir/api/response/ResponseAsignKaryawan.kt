package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseAsignKaryawan(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("reservasi")
	val reservasi: AsignReservasi? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class AsignReservasi(

	@field:SerializedName("jam_reservasi")
	val jamReservasi: String? = null,

	@field:SerializedName("tanggal_reservasi")
	val tanggalReservasi: String? = null,

	@field:SerializedName("karyawan_id")
	val karyawanId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("users_id")
	val usersId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status_reservasi")
	val statusReservasi: String? = null,

	@field:SerializedName("detail_reservasi")
	val detailReservasi: String? = null,

	@field:SerializedName("kendaraan_reservasi")
	val kendaraanReservasi: String? = null,

	@field:SerializedName("jeniskendala_reservasi")
	val jeniskendalaReservasi: String? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
