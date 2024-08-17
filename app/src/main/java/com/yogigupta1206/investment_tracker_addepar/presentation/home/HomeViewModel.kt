package com.yogigupta1206.investment_tracker_addepar.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogigupta1206.investment_tracker_addepar.domain.repository.InvestmentRepo
import com.yogigupta1206.investment_tracker_addepar.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: InvestmentRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private var job: Job? = null

    init {
        fetchData()
    }

    fun fetchData() {
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = viewModelScope.launch {
            repository.getInvestments().collect {
                    when (it) {
                        is Response.Error -> {
                            _uiState.value =
                                _uiState.value.copy(isLoading = false, errorMessage = it.message)
                        }

                        is Response.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }

                        is Response.Success -> {
                            _uiState.value = _uiState.value.copy(
                                investmentList = it.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                }
        }
    }

}
