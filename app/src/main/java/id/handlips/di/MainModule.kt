package id.handlips.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.handlips.BuildConfig
import id.handlips.data.local.DataStorePreference
import id.handlips.data.remote.ApiService
import id.handlips.data.repository.AuthRepository
import id.handlips.data.repository.HistoryRepository
import id.handlips.data.repository.ProfileRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    object DataStoreModule {
        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext context: Context): DataStorePreference {
            return DataStorePreference(context)
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository {
        return AuthRepository(auth)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        apiService: ApiService,
        auth: AuthRepository
    ): ProfileRepository {
        return ProfileRepository (apiService)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(
        apiService: ApiService,
        @ApplicationContext context: Context
    ): HistoryRepository {
        return HistoryRepository(apiService, context)
    }


}