package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<DataHistory> = listOf(),

	@field:SerializedName("status")
	val status: String
)

data class DataHistory(

	@field:SerializedName("detection_type")
	val detectionType: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("message")
	val message: List<MessageItem?> = listOf(),

	@field:SerializedName("email")
	val email: String
)

data class MessageItem(

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("is_speech_to_text")
	val isSpeechToText: Boolean
)
