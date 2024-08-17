package com.yogigupta1206.investment_tracker_addepar.presentation.home

import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment

data class HomeScreenUiState(
    val investmentList: List<Investment> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
