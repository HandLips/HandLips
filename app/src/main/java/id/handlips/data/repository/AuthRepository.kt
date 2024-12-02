package id.handlips.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import id.handlips.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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

    suspend fun sendPasswordReset(email: String): Resource<Unit> =
        try {
            auth.sendPasswordResetEmail(email).await()
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

    fun checkEmailVerification(onVerified: () -> Unit, onUnverified: () -> Unit) {
        val user = auth.currentUser
        user?.reload() // Reload untuk memastikan data terbaru
        if (user?.isEmailVerified == true) {
            onVerified()
        } else {
            onUnverified()
        }
    }

    suspend fun signInWithGoogle(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading)
            val result = auth.signInWithCredential(credential).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message ?: "An error occurred"))
        }
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun isUserAuthenticated(): Boolean = auth.currentUser != null
}
