package com.example.memes.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class Memes(
    @SerialName("count")
    val count: Int,
    @SerialName("memes")
    val memes: List<Meme>
)
