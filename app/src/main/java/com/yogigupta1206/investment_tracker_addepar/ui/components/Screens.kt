package com.yogigupta1206.investment_tracker_addepar.ui.components


sealed class Screens(val route: String) {
    data object HomePage : Screens("homepage")
    data object InvestmentDetailsPage : Screens("investmentDetails")
}