package com.example.plango.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plango.R
import com.example.plango.ui.components.button.PlanGoButton
import com.example.plango.ui.components.trip.PlanGoTripCard
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.Typography
import com.example.plango.viewmodel.TripViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    tripNavHostController: NavController,
    tripViewModel: TripViewModel
) {
    val trips by tripViewModel.trips.collectAsState() // Observa a lista de viagens
    val isLoading = tripViewModel.isLoading

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Minhas viagens",
            style = Typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )

        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
        } else if (trips.isEmpty()) {
            Text(
                text = "Você ainda não criou nenhuma viagem!",
                style = Typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(trips) { trip ->
                    PlanGoTripCard(
                        trip = trip,
                        navController = navController
                    )
                }
            }
        }

        PlanGoButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Criar nova viagem",
            iconRes = R.drawable.ic_add,
            onClick = { tripNavHostController.navigate(Routes.ADD_TRIP) }
        )
    }
}
