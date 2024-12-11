package id.handlips.data.repository

import id.handlips.data.model.GeminiResponse
import id.handlips.data.model.Topic
import id.handlips.data.remote.GeminiService
import id.handlips.utils.Resource
import javax.inject.Inject

class GeminiRepository @Inject constructor(
    private val geminiService: GeminiService
){

    suspend fun generateChatBot(topic: Topic) : Resource<GeminiResponse> {
        return try {
            val response = geminiService.generateEventicle(topic)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}