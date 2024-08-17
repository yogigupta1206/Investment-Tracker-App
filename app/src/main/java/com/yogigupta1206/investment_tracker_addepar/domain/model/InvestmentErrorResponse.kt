package com.yogigupta1206.investment_tracker_addepar.domain.model

import com.google.gson.annotations.SerializedName

data class InvestmentErrorResponse(
    val error: String? = null,
    @SerializedName("error_description") val errorDescription: String? = null
)