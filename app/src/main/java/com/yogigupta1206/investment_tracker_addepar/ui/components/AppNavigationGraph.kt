package com.yogigupta1206.investment_tracker_addepar.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigationGraph() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.HomePage.route,
        modifier = Modifier
    ) {

        composable(Screens.HomePage.route) {
            //HomeScreen(onNavigateToMovieDetails = { navController.onNavigateToInvestmentDetails() })
        }

        composable(Screens.InvestmentDetailsPage.route) {
            //InvestmentDeatilsPage(onNavigateBack = { navController.popBackStack() })
        }


    }
}

fun NavController.onNavigateToInvestmentDetails() =
    navigate(Screens.InvestmentDetailsPage.route)

