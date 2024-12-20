package id.handlips.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.handlips.BuildConfig
import id.handlips.data.local.DataStorePreference
import id.handlips.data.remote.ApiService
import id.handlips.data.remote.SpeechToTextApiService
import id.handlips.data.remote.GeminiService
import id.handlips.data.repository.AuthRepository
import id.handlips.data.repository.HistoryRepository
import id.handlips.data.repository.ProfileRepository
import id.handlips.data.repository.SpeechToTextRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
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

    @DefaultRetrofit
    @Provides
    @Singleton
    fun provideMainRetrofit(client: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @SpeechToTextRetrofit
    @Provides
    fun provideSpeechRetrofit(): Retrofit =
        Retrofit
            .Builder()
            .baseUrl("https://jayajaya.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideSpeechToTextApiService(
        @SpeechToTextRetrofit retrofit: Retrofit,
    ): SpeechToTextApiService = retrofit.create(SpeechToTextApiService::class.java)

    @Provides
    fun provideSpeechToTextRepository(api: SpeechToTextApiService): SpeechToTextRepository = SpeechToTextRepository(api)

    @Provides
    @Singleton
    fun provideApiService(
        @DefaultRetrofit retrofit: Retrofit,
    ): ApiService = retrofit.create(ApiService::class.java)

    @GeminiRetrofit
    @Provides
    @Singleton
    fun provideGemini(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GEMINI_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideGeminiService(@GeminiRetrofit retrofit: Retrofit): GeminiService =
        retrofit.create(GeminiService::class.java)


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepository(auth)

    @Provides
    @Singleton
    fun provideProfileRepository(
        apiService: ApiService,
        auth: AuthRepository,
    ): ProfileRepository = ProfileRepository(apiService)


    @Provides
    @Singleton
    fun provideHistoryRepository(
        apiService: ApiService,
        @ApplicationContext context: Context,
    ): HistoryRepository = HistoryRepository(apiService, context)
}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStorePreference = DataStorePreference(context)
}

@Module
@InstallIn(SingletonComponent::class)
object GoogleSignInModule {
    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
    ): GoogleSignInClient {
        val gso =
            GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.API_KEY)
                .requestEmail()
                .build()

        return GoogleSignIn.getClient(context, gso)
    }
}
