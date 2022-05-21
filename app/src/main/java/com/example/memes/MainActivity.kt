package com.example.memes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.memes.databinding.ActivityMainBinding
import com.example.memes.domain.MemeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MemeViewModel>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setUI()

        binding.btnLoadMeme.setOnClickListener{
            viewModel.getMeme()
        }
    }

    private fun setUI(){
        lifecycleScope.launchWhenCreated {
            viewModel.memeState.collectLatest {state->
                when{
                    state.isLoading ->{
                        binding.ivMeme.visibility = View.INVISIBLE
                        binding.loadingBar.visibility = View.VISIBLE
                        binding.btnLoadMeme.isEnabled = false
                    }
                    state.error.isNotBlank() ->{
                        binding.ivMeme.visibility = View.INVISIBLE
                        binding.loadingBar.visibility = View.INVISIBLE
                        binding.btnLoadMeme.isEnabled = true

                        Snackbar.make(binding.root,state.error,Snackbar.LENGTH_LONG)
                            .setAction("Retry") {
                                viewModel.getMeme()
                            }.show()
                    }

                    state.link.isNotEmpty() ->{
                        binding.ivMeme.visibility = View.VISIBLE
                        binding.loadingBar.visibility = View.INVISIBLE
                        binding.btnLoadMeme.isEnabled = true

                        Glide.with(binding.root).load(state.link).into(binding.ivMeme)
                    }
                }

            }
        }
    }
}