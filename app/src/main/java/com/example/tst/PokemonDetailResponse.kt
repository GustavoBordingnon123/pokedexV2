package com.example.tst

data class PokemonDetailResponse(
    val abilities: List<AbilityDetail>,
    val base_experience: Int,
    val sprites: SpritesDetail,
    val types: List<TypeDetail>,
    val weight: Int
)

data class AbilityDetail(
    val ability: AbilityInfo,
    val is_hidden: Boolean,
    val slot: Int
)

data class AbilityInfo(
    val name: String,
    val url: String
)

data class SpritesDetail(
    val front_default: String?,
    val back_default: String?,
)

data class TypeDetail(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String,
    val url: String
)
