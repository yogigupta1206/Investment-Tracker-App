package com.yogigupta1206.investment_tracker_addepar.presentation.investment_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogigupta1206.investment_tracker_addepar.R
import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment
import com.yogigupta1206.investment_tracker_addepar.presentation.SharedInvestmentViewModel
import com.yogigupta1206.investment_tracker_addepar.presentation.components.InvestmentCalculations
import com.yogigupta1206.investment_tracker_addepar.ui.theme.LocalExtendedColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

@Composable
fun InvestmentDetailsScreen(
    viewModel: SharedInvestmentViewModel = hiltViewModel(),
    onNavigateBack:() -> Unit
) {

    val state = viewModel.investmentDetailUiState.value

    if (state?.isLoading == true) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (!state?.error.isNullOrBlank()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state?.error.toString(), color = Color.Red)
        }
    } else {
        state?.investment?.let { InvestmentDetailsContent(it, onNavigateBack) }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentDetailsContent(investment: Investment, onNavigateBack: () -> Unit) {

    var calculatedData by remember(investment) {
        mutableStateOf(InvestmentCalculations(0.0, 0.0, 0.0, 0.0))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.investment_details)) },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = investment.name ?: stringResource(id = R.string.empty_string),
                style = MaterialTheme.typography.titleMedium
            )
            if (!investment.ticker.isNullOrBlank()) {
                Text(
                    text = "Ticker: ${investment.ticker}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            LaunchedEffect(investment) {
                val calculatedValues = withContext(Dispatchers.Default) {
                    val currentValue = investment.value?.toDoubleOrNull() ?: 0.0
                    val principalValue = investment.principal?.toDoubleOrNull() ?: 0.0
                    val profitLoss = currentValue - principalValue
                    val profitLossPercentage = (profitLoss / principalValue) * 100
                    InvestmentCalculations(currentValue, principalValue, profitLoss, profitLossPercentage)
                }
                calculatedData = calculatedValues
            }

            Text(
                text = "Current Value: $${"%.2f".format(calculatedData.currentValue)}",    // Can't be Two Source of Truth
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Principal: $${"%.2f".format(calculatedData.principalValue)}",      // Can't be Two Source of Truth
                style = MaterialTheme.typography.bodyLarge
            )
            Row {
                Text(
                    text = "Profit/Loss: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${if (calculatedData.profitLoss >= 0) "+" else "-"}$${"%.2f".format(calculatedData.profitLoss.absoluteValue)}",
                    color = if (calculatedData.profitLoss >= 0) LocalExtendedColors.current.success.color else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = " ( ${"%.2f".format(calculatedData.profitLossPercentage)}% )",
                    color = if (calculatedData.profitLoss >= 0) LocalExtendedColors.current.success.color else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "Details:",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = investment.details ?: stringResource(id = R.string.empty_string),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}