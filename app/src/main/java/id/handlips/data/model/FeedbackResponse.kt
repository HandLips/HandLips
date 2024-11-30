package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class FeedbackResponse(

	@field:SerializedName("data")
	val data: DataFeedback,

	@field:SerializedName("success")
	val success: Boolean
)

data class DataFeedback(

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("comment")
	val comment: String,

	@field:SerializedName("id")
	val id: Int
)
