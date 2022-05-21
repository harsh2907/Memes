package com.example.memes.di

import com.example.memes.data_source.response.MemeResponse
import com.example.memes.domain.MemeRepository
import com.example.memes.domain.MemeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    //For Single Instance
    single{
        Retrofit.Builder()
            .baseUrl("https://meme-api.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MemeResponse::class.java)
    }
    single{
        MemeRepository(get())
    }
    viewModel {
        MemeViewModel(get())
    }

}