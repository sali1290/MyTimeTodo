package com.example.mytimetodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Work
import com.example.domain.usecase.GetAllWorks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.mytimetodo.utility.Result
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getAllWorks: GetAllWorks) : ViewModel() {

    private val _works = MutableLiveData<Result<List<Work>>>()
    val works: LiveData<Result<List<Work>>>
        get() = _works

    fun getAllWorks() = viewModelScope.launch(Dispatchers.IO) {
        _works.postValue(Result.Loading)
        try {
            _works.postValue(
                Result.Success(getAllWorks.invoke())
            )
        } catch (e: IOException) {
            _works.postValue(
                Result.Error(
                    e.message ?: "Something went wrong! please try again later."
                )
            )
        }
    }

}