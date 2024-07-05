package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseDetailBengkel(

	@field:SerializedName("jam_operasional")
	val jamOperasional: List<JamOperasionalItem?>? = null,

	@field:SerializedName("status_favorit")
	val statusFavorit: String? = null,

	@field:SerializedName("jenis_layanan")
	val jenisLayanan: List<JenisLayananItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("bengkel")
	val bengkel: Bengkel? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class JenisLayananItem(

	@field:SerializedName("harga_layanan")
	val hargaLayanan: Int? = null,

	@field:SerializedName("jenis_layanan")
	val jenisLayanan: List<String?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nama_layanan")
	val namaLayanan: String? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable

@Parcelize
data class Bengkel(

	@field:SerializedName("jam_buka")
	val jamBuka: String? = null,

	@field:SerializedName("jam_tutup")
	val jamTutup: String? = null,

	@field:SerializedName("alamat_bengkel")
	val alamatBengkel: String? = null,

	@field:SerializedName("nama_bengkel")
	val namaBengkel: String? = null,

	@field:SerializedName("lokasi_bengkel")
	val lokasiBengkel: String? = null,

	@field:SerializedName("gmaps_bengkel")
	val gmapsBengkel: String? = null,

	@field:SerializedName("users_id")
	val usersId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("number_bengkel")
	val numberBengkel: String? = null,

	@field:SerializedName("jenis_kendaraan")
	val jenisKendaraan: List<String?>? = null,

	@field:SerializedName("hari_operasional")
	val hariOperasional: List<String?>? = null
) : Parcelable

@Parcelize
data class JamOperasionalItem(

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
