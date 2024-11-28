package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class SoundResponse(

	@field:SerializedName("data")
	val data: List<DataItem> = listOf(),

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("audioUrl")
	val audioUrl: String,

	@field:SerializedName("fileName")
	val fileName: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("text")
	val text: String,
)
