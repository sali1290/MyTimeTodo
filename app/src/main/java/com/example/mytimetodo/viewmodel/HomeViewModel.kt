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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getAllWorks: GetAllWorks) : ViewModel() {

    private val _works = MutableLiveData<Result<Work>>()
    val works: LiveData<Result<Work>>
        get() = _works

    fun getAllWorks() = viewModelScope.launch(Dispatchers.IO) {

    }

}