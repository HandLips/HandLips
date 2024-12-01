package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class ReportResponse(

	@field:SerializedName("data")
	val data: DataReport,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataReport(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("id")
	val id: String
)
