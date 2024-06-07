package com.dicoding.mystoryapp.data.Response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
