package com.example.tugasakhir.api.response

import com.google.gson.annotations.SerializedName

data class ResponseUpdateJenisLayanan(

	@field:SerializedName("jenis_layanan")
	val jenisLayanan: UpdateJenisLayanan? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class UpdateJenisLayanan(

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
)
