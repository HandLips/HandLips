package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class Topic(
	@field:SerializedName("topic")
	val topic: String
)

data class GeminiResponse(
	@field:SerializedName("response")
	val response: String,
)
