package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class ListSpeechToTextResponse(
    @field:SerializedName("data")
    val data: List<SpeechToTextData> = listOf<SpeechToTextData>(),
    @field:SerializedName("success")
    val success: Boolean,
)
