package com.example.memes.data_source.response

import com.example.memes.data_source.model.MemeDTO
import retrofit2.Response
import retrofit2.http.GET

interface MemeResponse {

    @GET("/gimme")
    suspend fun getMeme():MemeDTO

}