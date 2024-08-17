package com.yogigupta1206.investment_tracker_addepar.presentation.investment_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogigupta1206.investment_tracker_addepar.R
import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment
import com.yogigupta1206.investment_tracker_addepar.presentation.SharedInvestmentViewModel

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
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {

            Text(text = investment.name ?: stringResource(R.string.empty_string),
                lineHeight = 32.sp,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = investment.ticker ?: stringResource(R.string.empty_string),
                lineHeight = 24.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = investment.principal ?: stringResource(R.string._0),
                lineHeight = 24.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = investment.value ?: stringResource(R.string._0),
                lineHeight = 24.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = investment.details ?: stringResource(R.string.empty_string),
                lineHeight = 24.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
        }
    }
}