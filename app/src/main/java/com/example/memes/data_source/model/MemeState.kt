package com.example.memes.data_source.model

data class MemeState(
    val link:String = "",
    val isLoading:Boolean = false,
    val error:String = ""
)