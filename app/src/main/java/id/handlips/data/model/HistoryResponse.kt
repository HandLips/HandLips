package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<DataHistory> = listOf(),

	@field:SerializedName("success")
	val success: Boolean
)

data class DataHistory(

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("message")
	val message: List<String>?
)
