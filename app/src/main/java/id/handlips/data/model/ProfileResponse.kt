package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: DataProfile?
)

data class DataProfile(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("profile_picture_url")
	val profile_picture_url: String,

	@field:SerializedName("createdAt")
	val created_at: String,
)

