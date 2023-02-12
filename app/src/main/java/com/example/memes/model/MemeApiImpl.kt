package com.example.memes.model

import android.util.Log
import com.example.memes.util.Resource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*

class MemeApiImpl(private val client: HttpClient) : MemeApi {
    override suspend fun getMemesList(memesCount: Int): Resource<List<Meme>> {
        return try {
            val result = client.get { url("${Routes.GIMME}/$memesCount") }
            val memesData: Memes = result.body()
            Resource.Success(memesData.memes)
        } catch (e: RedirectResponseException) {
            Log.e("MemesApi", "3XX Error: ${e.message}")
            Resource.Error(e.localizedMessage ?: e.message)
        } catch (e: ClientRequestException) {
            Log.e("MemesApi", "4XX Error: ${e.message}")
            Resource.Error(e.localizedMessage ?: e.message)
        } catch (e: ServerResponseException) {
            Log.e("MemesApi", "5XX Error: ${e.message}")
            Resource.Error(e.localizedMessage ?: e.message)
        } catch (e: Exception) {
            Log.e("MemesApi", "Error: ${e.message}")
            Resource.Error(e.localizedMessage ?: e.message!!)
        }
    }

    override suspend fun getMemeListBySubredit(subredit: String,memesCount: Int): Resource<List<Meme>> {
        return try {
            val result = client.get { url("${Routes.GIMME}/$subredit/$memesCount") }
            val memesData: Memes = result.body()
            Resource.Success(memesData.memes)
        } catch (e: RedirectResponseException) {
            Log.e("MemesApi", "3XX Error: ${e.message}")
            Resource.Error(e.localizedMessage ?: e.message)
        } catch (e: ClientRequestException) {
            Log.e("MemesApi", "4XX Error: ${e.message}")
            Resource.Error(e.localizedMessage ?: e.message)
        } catch (e: ServerResponseException) {
            Log.e("MemesApi", "5XX Error: ${e.message}")
            Resource.Error(e.localizedMessage ?: e.message)
        } catch (e: Exception) {
            Log.e("MemesApi", "Error: ${e.message}")
            Resource.Error(e.localizedMessage ?: e.message!!)
        }
    }

}