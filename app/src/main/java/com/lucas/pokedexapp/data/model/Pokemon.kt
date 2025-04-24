package com.lucas.pokedexapp.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<TypeSlot>
)

data class Sprites(
    @SerializedName("other")
    val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String
)

data class TypeSlot(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)
