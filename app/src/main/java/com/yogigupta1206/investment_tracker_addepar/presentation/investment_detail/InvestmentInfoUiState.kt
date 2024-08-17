package com.yogigupta1206.investment_tracker_addepar.presentation.investment_detail

import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment

data class InvestmentInfoUiState(
    val investment: Investment? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
