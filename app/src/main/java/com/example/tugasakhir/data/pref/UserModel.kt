package com.example.tugasakhir.data.pref

data class UserModel(
    val username: String,
    val isLogin: Boolean = false,
    val id: Int = 0,
    val user_bengkel: String,
    val bengkels_id: Int = 0,
)