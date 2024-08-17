package com.yogigupta1206.investment_tracker_addepar.data.repository

import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment
import com.yogigupta1206.investment_tracker_addepar.domain.repository.InvestmentRepo
import com.yogigupta1206.investment_tracker_addepar.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InvestmentRepoImpl@Inject constructor() : InvestmentRepo {

    companion object{
        val TAG: String = InvestmentRepoImpl::class.java.simpleName
    }

    override fun getInvestments(): Flow<Response<List<Investment>>> {
        return flow {  }.flowOn(Dispatchers.IO)
    }

}