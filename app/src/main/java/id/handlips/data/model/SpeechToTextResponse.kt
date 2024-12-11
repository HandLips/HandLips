package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class SpeechToTextResponse(
    @field:SerializedName("data")
    val data: SpeechToTextData,
    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("message")
    val message: String,
)

data class SpeechToTextData(
    @field:SerializedName("createdByEmail")
    val createdByEmail: String,
    @field:SerializedName("createdAt")
    val createdAt: String,
    @field:SerializedName("audioUrl")
    val audioUrl: String,
    @field:SerializedName("fileName")
    val fileName: String,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("text")
    val text: String,
    @field:SerializedName("updatedAt")
    val updatedAt: String,
)
