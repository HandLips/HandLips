package id.handlips.data.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import id.handlips.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository
    @Inject
    constructor(
        private val auth: FirebaseAuth,
    ) {
        suspend fun createAccount(
            email: String,
            password: String,
        ): Resource<FirebaseUser> =
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                Resource.Success(result.user!!)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }

        suspend fun signIn(
            email: String,
            password: String,
        ): Resource<FirebaseUser> =
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(result.user!!)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }

        suspend fun sendEmailVerification(): Resource<Unit> =
            try {
                auth.currentUser?.sendEmailVerification()?.await()
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }

        suspend fun updatePassword(newPassword: String): Resource<Unit> =
            try {
                auth.currentUser?.updatePassword(newPassword)?.await()
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }

        suspend fun reauthenticateUser(
            email: String,
            password: String,
        ): Resource<Unit> =
            try {
                val credential = EmailAuthProvider.getCredential(email, password)
                auth.currentUser?.reauthenticate(credential)?.await()
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Error(e.message ?: "An error occurred")
            }

        fun logout() {
            auth.signOut()
        }

        fun getCurrentUser(): FirebaseUser? = auth.currentUser

        fun isUserAuthenticated(): Boolean = auth.currentUser != null
    }
