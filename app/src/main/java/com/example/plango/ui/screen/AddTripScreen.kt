package com.example.plango.ui.screen

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plango.R
import com.example.plango.data.repository.TripRepository
import com.example.plango.data.model.Trip
import com.example.plango.ui.components.button.PlanGoButton
import com.example.plango.ui.components.textfield.PlanGoTextField
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.utils.formatToDate
import com.example.plango.viewmodel.TripViewModel
import com.google.firebase.Timestamp
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun AddTripScreen(
    tripViewModel: TripViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    var tripData by remember { mutableStateOf(Trip(id = UUID.randomUUID().toString())) }

    // Estados para exibir o DatePicker
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.img_travelers),
                contentDescription = "Travelers Image",
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "Adicionar viagem",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Destino
            PlanGoTextField(
                value = tripData.destination.orEmpty(),
                onValueChange = { tripData = tripData.copy(destination = it) },
                placeholder = "Digite o destino",
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_map_pin),
                        contentDescription = "Destino",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
            // Nome da viagem
            PlanGoTextField(
                value = tripData.name.orEmpty(),
                onValueChange = { tripData = tripData.copy(name = it) },
                placeholder = "Como você quer chamar a viagem?",
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Nome da viagem",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
            // Breve descrição
            PlanGoTextField(
                value = tripData.description.orEmpty(),
                onValueChange = { tripData = tripData.copy(description = it) },
                placeholder = "Breve descrição (opcional)",
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_description),
                        contentDescription = "Descrição",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
            // Data de ida e volta
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(text = "Data de ida")
                    PlanGoButton(
                        text = tripData.startDate.formatToDate(),
                        onClick = { showStartDatePicker = true },
                        modifier = Modifier.fillMaxWidth(),
                        upperCase = false
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(text = "Data de volta")
                    PlanGoButton(
                        text = tripData.endDate.formatToDate(),
                        onClick = { showEndDatePicker = true },
                        modifier = Modifier.fillMaxWidth(),
                        upperCase = false
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        PlanGoButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Adicionar Viagem",
            onClick = {
                Log.d("AddTripScreen", "tripData: $tripData")
                tripViewModel.createTrip(
                    name = tripData.name.orEmpty(),
                    description = tripData.description.orEmpty(),
                    startDate = tripData.startDate,
                    endDate = tripData.endDate,
                    destination = tripData.destination.orEmpty(),
                    imageUrl = null,
                    onSuccess = {
                        navController.navigate(Routes.HOME)
                    }
                )
            },
            upperCase = false,
            iconRes = R.drawable.ic_checkcircle,
            isLoading = tripViewModel.isLoading
        )

        if (showStartDatePicker) {
            ShowDatePicker(context) { selectedTimestamp ->
                tripData = tripData.copy(startDate = selectedTimestamp)
                showStartDatePicker = false
            }
        }

        if (showEndDatePicker) {
            ShowDatePicker(context) { selectedTimestamp ->
                tripData = tripData.copy(endDate = selectedTimestamp)
                showEndDatePicker = false
            }
        }
    }
}

@Composable
fun ShowDatePicker(context: Context, onDateSelected: (Timestamp) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
            val timestamp = Timestamp(selectedCalendar.time)
            onDateSelected(timestamp)
        },
        year, month, day
    ).show()
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun AddTripScreenPreview() {
    PlanGoTheme {
        AddTripScreen(
            navController = NavController(LocalContext.current),
            tripViewModel = TripViewModel(
                repository = TripRepository()
            )
        )
    }
}