package com.example.memes.domain

import com.example.memes.data_source.model.MemeDTO
import com.example.memes.data_source.response.MemeResponse
import com.example.memes.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class MemeRepository(
    private val api:MemeResponse
) {

     fun getMeme():Flow<Response<MemeDTO>> = flow {
         try {
             emit(Response.Loading())
             val response = api.getMeme()
             emit(Response.Success(response))

         }catch (e: HttpException){
             emit(Response.Error(message = "Oops, something went wrong"))
         }
         catch (e: IOException){
             emit(Response.Error(message = "Couldn't reach server check your internet connection"))
         }


    }

}