package com.yogigupta1206.investment_tracker_addepar.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment
import com.yogigupta1206.investment_tracker_addepar.domain.model.InvestmentErrorResponse
import com.yogigupta1206.investment_tracker_addepar.domain.model.InvestmentsSuccessResponse
import com.yogigupta1206.investment_tracker_addepar.domain.repository.InvestmentRepo
import com.yogigupta1206.investment_tracker_addepar.utils.AssetJsonReader
import com.yogigupta1206.investment_tracker_addepar.utils.Response
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InvestmentRepoImpl@Inject constructor(
    @ApplicationContext private val context: Context
) : InvestmentRepo {

    companion object{
        val TAG: String = InvestmentRepoImpl::class.java.simpleName
    }

    override fun getInvestments(): Flow<Response<List<Investment>>> = flow {
        emit(Response.Loading())
        val jsonString = AssetJsonReader.readJSONFromAssets(context, "path")
        try {
            val gson = Gson()
            val response = gson.fromJson(jsonString, InvestmentsSuccessResponse::class.java)
            if (response.investments != null) {
                emit(Response.Success(response.investments))
            } else {
                val errorResponse = gson.fromJson(jsonString, InvestmentErrorResponse::class.java)
                Log.e(TAG, "${errorResponse.error ?: "Unknown Error"} : ${errorResponse.errorDescription}")
                emit(Response.Error(errorResponse.error ?: "Unknown Error"))
            }
        }
        catch (e: JsonSyntaxException){
            Log.e(TAG, "JsonSyntaxException: ${e.message}",e)
            emit(Response.Error("Invalid JSON format"))
        }
        catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}", e)
            emit(Response.Error("Exception occurred"))
        }
        }.flowOn(Dispatchers.IO)

}