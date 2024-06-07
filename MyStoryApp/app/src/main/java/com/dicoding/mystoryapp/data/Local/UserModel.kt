package com.dicoding.mystoryapp.data.Local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var userId: String,
    var name: String,
    var token: String,
    var isLogin: Boolean = false
): Parcelable
