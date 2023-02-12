package com.example.memes.model

import com.example.memes.util.Resource

interface MemeApi {
    suspend fun getMemesList(memesCount: Int): Resource<List<Meme>>
    suspend fun getMemeListBySubredit(subredit: String, memesCount: Int): Resource<List<Meme>>
}