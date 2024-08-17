package com.yogigupta1206.investment_tracker_addepar.domain.repository

import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment
import com.yogigupta1206.investment_tracker_addepar.utils.Response
import kotlinx.coroutines.flow.Flow

interface InvestmentRepo {
    fun  getInvestments(): Flow<Response<List<Investment>>>
}