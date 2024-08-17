package com.yogigupta1206.investment_tracker_addepar.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogigupta1206.investment_tracker_addepar.presentation.home.components.InvestmentItem

@Composable
fun HomeScreen(
    onNavigateToInvestmentDetail: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        content = { padding ->
            HomeScreenContent(
                padding,
                uiState,
                onNavigateToInvestmentDetail,
                viewModel::fetchData
            )
        }
    )
}

@Composable
fun HomeScreenContent(
    padding: PaddingValues,
    uiState: HomeScreenUiState,
    onNavigateToInvestmentDetails: ()-> Unit,
    onRetry: () -> Unit
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
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 30.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(17.dp),
                    horizontalArrangement = Arrangement.spacedBy(17.dp)
                ) {
                    items(uiState.investmentList){
                        InvestmentItem(it, onClick = {
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
        Text(text = message, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}