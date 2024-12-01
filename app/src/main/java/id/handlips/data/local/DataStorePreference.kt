package id.handlips.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStorePreference @Inject constructor(@ApplicationContext private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    // Save onboarding state
    suspend fun saveOnBoardingState(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.onBoardingCompleted] = completed
        }
    }

    // Read onboarding state
    fun readOnBoardingState(): Flow<Boolean> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKeys.onBoardingCompleted] ?: false
            }
    }

    private object PreferencesKeys {
        val onBoardingCompleted = booleanPreferencesKey("onboarding_completed")
    }
}
