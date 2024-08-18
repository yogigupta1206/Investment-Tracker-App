package com.yogigupta1206.investment_tracker_addepar.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yogigupta1206.investment_tracker_addepar.R
import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment
import com.yogigupta1206.investment_tracker_addepar.presentation.common_components.InvestmentCalculations
import com.yogigupta1206.investment_tracker_addepar.ui.theme.LocalExtendedColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun InvestmentItem(investment: Investment, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Text(
                    text = investment.name ?: stringResource(id = R.string.empty_string),
                    style = MaterialTheme.typography.titleMedium
                )
                if (!investment.ticker.isNullOrBlank()) {
                    Text(
                        text = " (${investment.ticker})",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            val calcultedData = remember(investment) {
                mutableStateOf(InvestmentCalculations(0.0, 0.0, 0.0, 0.0))
            }

            LaunchedEffect(investment) {
                val calculatedValues = withContext(Dispatchers.Default) {
                    val currentValue = investment.value?.toDoubleOrNull() ?: 0.0
                    val principalValue = investment.principal?.toDoubleOrNull() ?: 0.0
                    val profitLoss = currentValue - principalValue
                    val profitLossPercentage = (profitLoss / principalValue) * 100
                    InvestmentCalculations(currentValue, principalValue, profitLoss, profitLossPercentage)
                }
                calcultedData.value = calculatedValues
            }

            Row{
                Text(
                    text = "Current Value: ",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "$${"%.2f".format(calcultedData.value.currentValue)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (calcultedData.value.profitLoss >= 0) LocalExtendedColors.current.success.color else MaterialTheme.colorScheme.error,
                )
            }

            Text(
                text = "Principal: $${"%.2f".format(calcultedData.value.principalValue)}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {

    InvestmentItem(
        Investment(
            name = "Test Name",
            details = "It is the Detail Section",
            principal = "5000000000",
            value = "8900000000",
            ticker = "NFLX"
        )
    ) {}
}