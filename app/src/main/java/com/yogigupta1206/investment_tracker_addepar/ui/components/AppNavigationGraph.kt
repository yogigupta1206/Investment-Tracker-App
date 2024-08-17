package com.yogigupta1206.investment_tracker_addepar.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yogigupta1206.investment_tracker_addepar.presentation.home.HomeScreen
import com.yogigupta1206.investment_tracker_addepar.presentation.SharedInvestmentViewModel
import com.yogigupta1206.investment_tracker_addepar.presentation.investment_detail.InvestmentDetailsScreen

@Composable
fun AppNavigationGraph() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route,
        modifier = Modifier
    ) {

        composable(Screens.HomePage.route) {
            HomeScreen(onNavigateToInvestmentDetail = { navController.onNavigateToInvestmentDetails() })
        }

        composable(Screens.InvestmentDetailsPage.route) {
            val backStackEntry = remember(navController) {
                navController.getBackStackEntry(Screens.HomePage.route)
            }
            val viewModel: SharedInvestmentViewModel = hiltViewModel(backStackEntry)
            InvestmentDetailsScreen(viewModel, onNavigateBack = { navController.popBackStack() })
        }


    }
}

fun NavController.onNavigateToInvestmentDetails() =
    navigate(Screens.InvestmentDetailsPage.route)

