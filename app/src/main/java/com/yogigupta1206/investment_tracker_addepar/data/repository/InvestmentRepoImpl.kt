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
import kotlin.jvm.Throws

class InvestmentRepoImpl@Inject constructor(
    @ApplicationContext private val context: Context
) : InvestmentRepo {

    companion object{
        val TAG: String = InvestmentRepoImpl::class.java.simpleName
    }

    override fun getInvestments(): Flow<Response<List<Investment>>> = flow {
        emit(Response.Loading())
        try {
            val jsonString = AssetJsonReader.readJSONFromAssets(context, "investments.json")
            val investmentList = parseInvestments(jsonString)
            emit(Response.Success(investmentList))
        }
        catch (e: NullInvestmentException){
            Log.e(TAG, "NullInvestmentException: ${e.message}",e)
            emit(Response.Error(e.message ?: "Unknown Error"))
        }
        catch (e: EmptyInvestmentException){
            Log.e(TAG, "EmptyInvestmentException: ${e.message}",e)
            emit(Response.Error(e.message ?: "Unknown Error"))
        }
        catch (e: MalformedDataException){
            Log.e(TAG, "MalformedDataException: ${e.message}",e)
            emit(Response.Error(e.message ?: "Unknown Error"))
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


    @Throws(EmptyInvestmentException::class, NullInvestmentException::class, MalformedDataException::class, JsonSyntaxException::class)
    private fun parseInvestments(jsonString: String): List<Investment>{
        val gson = Gson()
        val response = gson.fromJson(jsonString, InvestmentsSuccessResponse::class.java)
        val investments = response.investments

        when {
            investments == null -> {
                val errorResponse = gson.fromJson(jsonString, InvestmentErrorResponse::class.java)
                Log.e(TAG, "${errorResponse.error ?: "Unknown Error"} : ${errorResponse.errorDescription}")
                throw NullInvestmentException(errorResponse.errorDescription ?: "Unknown Error")
            }
            investments.isEmpty() -> {
                Log.e(TAG, "No Investment Found")
                throw EmptyInvestmentException("No Investment Found")
            }
            investments.any { it.name == null || it.value == null || it.details == null || it.principal == null } -> {
                Log.e(TAG, "Malformed JSON Response Received")
                throw MalformedDataException("Malformed JSON Response Received")
            }
            else -> {
                Log.d(TAG, "investmentList: $investments")
                return investments
            }
        }
    }

}