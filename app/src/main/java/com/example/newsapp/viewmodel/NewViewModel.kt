package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.entity.New
import com.example.newsapp.model.ifNotSuccessful
import com.example.newsapp.model.ifSuccessful
import com.example.newsapp.model.repository.NewRepository
import com.example.newsapp.model.utils.Utils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class NewViewModel(private val repository: NewRepository) : ViewModel() {

    private val _getNewsMu = MutableLiveData<New>()
    val getNewsLiveData: LiveData<New> = _getNewsMu

    private val _getErrorMu = MutableLiveData<String>()
    val getErrorLiveData: LiveData<String> = _getErrorMu

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _getErrorMu.value = Utils.UNKNOWN_ERROR
    }

    fun getNews() = viewModelScope.launch(exceptionHandler) {

        val result = repository.getNewsFromApi()

        result.ifSuccessful { new ->
            _getNewsMu.value = new
        }

        result.ifNotSuccessful { error ->
            _getErrorMu.value = error
        }

    }
}