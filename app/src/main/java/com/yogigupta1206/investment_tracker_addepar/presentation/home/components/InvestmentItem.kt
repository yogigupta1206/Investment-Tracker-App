package com.yogigupta1206.investment_tracker_addepar.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yogigupta1206.investment_tracker_addepar.R
import com.yogigupta1206.investment_tracker_addepar.domain.model.Investment

@Composable
fun InvestmentItem(investment: Investment, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Column{
            Text(text = investment.name?: stringResource(R.string.empty_string),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(vertical = 9.dp)
            )
            HorizontalDivider(thickness = 2.dp)
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