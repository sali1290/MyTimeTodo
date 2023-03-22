package com.example.mytimetodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Work
import com.example.domain.usecase.AddWork
import com.example.domain.usecase.DeleteWork
import com.example.domain.usecase.GetAllWorks
import com.example.domain.usecase.UpdateWork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.mytimetodo.utility.Result
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllWorks: GetAllWorks,
    private val addWork: AddWork,
    private val deleteWork: DeleteWork,
    private val updateWork: UpdateWork,
) : ViewModel() {

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

    private val _addResult = MutableLiveData<Boolean>()
    val addResult: LiveData<Boolean>
        get() = _addResult

    fun addWork(work: Work) = viewModelScope.launch(Dispatchers.IO) {
        _addResult.postValue(addWork.invoke(work))
    }

    private val _deleteResult = MutableLiveData<Boolean>()
    val deleteResult: LiveData<Boolean>
        get() = _deleteResult

    fun deleteWork(work: Work) = viewModelScope.launch(Dispatchers.IO) {
        _deleteResult.postValue(deleteWork.invoke(work))
    }

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean>
        get() = _updateResult

    fun updateWork(work: Work) = viewModelScope.launch(Dispatchers.IO) {
        _updateResult.postValue(updateWork.invoke(work))
    }


}