package hu.urban.david

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: String,
    val link: String,
    val approved: Boolean
)

val songs = mutableListOf<Song>()