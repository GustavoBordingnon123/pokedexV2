package com.example.tst

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PokemonDetailViewModel : ViewModel() {
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _abilities = MutableLiveData<String>()
    val abilities: LiveData<String> get() = _abilities

    private val _gameIndex = MutableLiveData<Int>()
    val gameIndex: LiveData<Int> get() = _gameIndex

    private val _types = MutableLiveData<String>()
    val types: LiveData<String> get() = _types

    private val _height = MutableLiveData<Int>()
    val height: LiveData<Int> get() = _height

    private val _weight = MutableLiveData<Int>()
    val weight: LiveData<Int> get() = _weight

    fun setPokemonDetails(
        imageUrl: String,
        name: String,
        abilities: String,
        gameIndex: Int,
        types: String,
        height: Int,
        weight: Int
    ) {
        _imageUrl.value = imageUrl
        _name.value = name
        _abilities.value = abilities
        _gameIndex.value = gameIndex
        _types.value = types
        _height.value = height
        _weight.value = weight
    }
}
