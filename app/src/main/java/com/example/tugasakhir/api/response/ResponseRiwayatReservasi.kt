package com.example.tugasakhir.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRiwayatReservasi(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("reservasi")
	val reservasi: List<ReservasiItem?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ReservasiItem(

	@field:SerializedName("jam_reservasi")
	val jamReservasi: String? = null,

	@field:SerializedName("kendaraan_id")
	val kendaraanId: Int? = null,

	@field:SerializedName("prioritas")
	val prioritas: Double? = null,

	@field:SerializedName("alamat_bengkel")
	val alamatBengkel: String? = null,

	@field:SerializedName("tanggal_reservasi")
	val tanggalReservasi: String? = null,

	@field:SerializedName("nama_bengkel")
	val namaBengkel: String? = null,

	@field:SerializedName("lokasi_bengkel")
	val lokasiBengkel: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("merek_kendaraan")
	val merekKendaraan: String? = null,

	@field:SerializedName("kendaraan_reservasi")
	val kendaraanReservasi: String? = null,

	@field:SerializedName("jeniskendala_reservasi")
	val jeniskendalaReservasi: Int? = null,

	@field:SerializedName("plat_kendaraan")
	val platKendaraan: String? = null,

	@field:SerializedName("karyawan_id")
	val karyawanId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("nama_karyawan")
	val namaKaryawan: String? = null,

	@field:SerializedName("users_id")
	val usersId: Int? = null,

	@field:SerializedName("gmaps_bengkel")
	val gmapsBengkel: String? = null,

	@field:SerializedName("jenis_layanan")
	val jenisLayanan: List<String?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("number_bengkel")
	val numberBengkel: String? = null,

	@field:SerializedName("status_reservasi")
	val statusReservasi: String? = null,

	@field:SerializedName("detail_reservasi")
	val detailReservasi: String? = null,

	@field:SerializedName("nama_layanan")
	val nama_layanan: String? = null,

	@field:SerializedName("harga_layanan")
	val harga_layanan: Int? = null,

	@field:SerializedName("bengkels_id")
	val bengkelsId: Int? = null
) : Parcelable
