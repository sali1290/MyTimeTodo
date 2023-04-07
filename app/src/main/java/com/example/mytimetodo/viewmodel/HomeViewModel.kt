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

    private val _doneWorks = MutableLiveData<Result<List<Work>>>()
    val doneWorks: LiveData<Result<List<Work>>>
        get() = _doneWorks

    fun getDoneWorks() = viewModelScope.launch(Dispatchers.IO) {
        _doneWorks.postValue(Result.Loading)
        try {
            val workList = getAllWorks.invoke().filter {
                it.isDone
            }
            _doneWorks.postValue(
                Result.Success(workList)
            )
        } catch (e: IOException) {
            _doneWorks.postValue(
                Result.Error(
                    e.message ?: "Something went wrong! please try again later."
                )
            )
        }
    }

    private val _dailyWorks = MutableLiveData<Result<List<Work>>>()
    val dailyWorks: LiveData<Result<List<Work>>>
        get() = _dailyWorks

    fun getDailyRoutineWorks() = viewModelScope.launch(Dispatchers.IO) {
        _dailyWorks.postValue(Result.Loading)
        try {
            val workList = getAllWorks.invoke().filter {
                it.time != null && !it.isDone
            }
            _dailyWorks.postValue(
                Result.Success(workList)
            )
        } catch (e: IOException) {
            _dailyWorks.postValue(
                Result.Error(
                    e.message ?: "Something went wrong! please try again later."
                )
            )
        }
    }

    private val _otherWorks = MutableLiveData<Result<List<Work>>>()
    val otherWorks: LiveData<Result<List<Work>>>
        get() = _otherWorks

    fun getOtherWorks() = viewModelScope.launch(Dispatchers.IO) {
        _otherWorks.postValue(Result.Loading)
        try {
            val workList = getAllWorks.invoke().filter {
                it.time == null
            }
            _otherWorks.postValue(
                Result.Success(workList)
            )
        } catch (e: IOException) {
            _otherWorks.postValue(
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