package com.example.tst

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val abilities: List<AbilityDetail>,
    val sprites: SpritesDetail,
    val types: List<TypeDetail>,
    val weight: Int,
    val height: Int,
    val game_indices: List<GameIndexDetail>
)

data class AbilityDetail(
    val ability: AbilityInfo
)

data class AbilityInfo(
    val name: String
)

data class SpritesDetail(
    val front_default: String?
)

data class TypeDetail(
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)

data class GameIndexDetail(
    val game_index: Int
)
