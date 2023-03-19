package com.example.mytimetodo.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mytimetodo.databinding.ActivityMainBinding
import com.example.mytimetodo.viewmodel.HomeViewModel
import com.example.mytimetodo.utility.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAllWorks()
        observe()
    }

    private fun observe() {
        viewModel.works.observe(this) {
            when (it) {

                is Result.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                }

                is Result.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
                }

                is Result.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

            }
        }

    }

}