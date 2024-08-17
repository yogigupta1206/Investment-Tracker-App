package com.yogigupta1206.investment_tracker_addepar.domain.model

data class Investment(
    val name: String = "",
    val details: String? = null,
    val principal: String? = null,
    val ticker: String? = null,
    val value: String? = null
)