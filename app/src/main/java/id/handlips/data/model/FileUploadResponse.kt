package id.handlips.data.model

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)