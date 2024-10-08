package com.yogigupta1206.investment_tracker_addepar.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogigupta1206.investment_tracker_addepar.R
import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment
import com.yogigupta1206.investment_tracker_addepar.presentation.SharedInvestmentViewModel
import com.yogigupta1206.investment_tracker_addepar.presentation.home.components.InvestmentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToInvestmentDetail: () -> Unit,
    viewModel: SharedInvestmentViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.investments)) })
        },
        content = { padding ->
            HomeScreenContent(
                padding,
                uiState,
                onNavigateToInvestmentDetail,
                viewModel::fetchData,
                viewModel::updateSelectedInvestment
            )
        }
    )
}

@Composable
fun HomeScreenContent(
    padding: PaddingValues,
    uiState: HomeScreenUiState,
    onNavigateToInvestmentDetails: ()-> Unit,
    onRetry: () -> Unit,
    onInvestmentSelection: (Investment) -> Unit,
) {
    Column(
        modifier = Modifier.padding(padding)
    ) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            !uiState.errorMessage.isNullOrBlank() && uiState.investmentList.isEmpty()-> {
                ErrorView(message = uiState.errorMessage, onRetry = onRetry)
            }
            else ->{
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                ) {
                    items(uiState.investmentList){
                        InvestmentItem(it, onClick = {
                            onInvestmentSelection(it)
                            onNavigateToInvestmentDetails()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(horizontal = 16.dp), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}