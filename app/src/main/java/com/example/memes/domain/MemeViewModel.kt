package com.example.memes.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memes.data_source.model.MemeState
import com.example.memes.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemeViewModel (
    private val repo: MemeRepository
 ):ViewModel() {

    private val _memeState:MutableStateFlow<MemeState> = MutableStateFlow(MemeState())
    val memeState = _memeState.asStateFlow()

    init {
        getMeme()
    }

    fun getMeme() {
        val memeIndex = 2
        viewModelScope.launch {
            repo.getMeme().collectLatest {response ->
                when(response){
                    is Response.Success ->{
                        _memeState.value = memeState.value.copy(
                            link = response.data?.preview?.get(memeIndex) ?: "",
                            isLoading = false,
                            error = ""
                        )
                    }
                    is Response.Loading ->{
                        _memeState.value = memeState.value.copy(
                            link = response.data?.preview?.get(memeIndex) ?: "",
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Error ->{
                        _memeState.value = memeState.value.copy(
                            link = response.data?.preview?.get(memeIndex) ?: "",
                            isLoading = false,
                            error = response.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }
        }
    }

}