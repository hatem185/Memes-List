package com.example.memes.di

import android.content.Context
import com.example.memes.model.MemeApi
import com.example.memes.model.MemeApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides //tells dagger hilt this is a recipe ;)
    @Singleton //only one instance will be shared between our viewModels
    fun provideKtorClient(): HttpClient {
        val client = HttpClient(Android) {
            expectSuccess = true
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
        return client
    }

    @Provides
    @Singleton
    fun providePostApi(httpClient: HttpClient): MemeApi {
        return MemeApiImpl(httpClient)
    }


}