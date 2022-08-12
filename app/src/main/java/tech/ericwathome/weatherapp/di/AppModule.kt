package tech.ericwathome.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import tech.ericwathome.weatherapp.util.API_KEY
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val apiInterceptor = Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder().addHeader(
                "apikey", API_KEY
            ).build()
            chain.proceed(request)
        }
        return OkHttpClient().newBuilder()
            .addInterceptor(apiInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}