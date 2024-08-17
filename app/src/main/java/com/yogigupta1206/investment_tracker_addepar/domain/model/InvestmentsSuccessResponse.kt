package com.yogigupta1206.investment_tracker_addepar.domain.model

data class InvestmentsSuccessResponse(
    val investments: List<Investment>? = emptyList()
)