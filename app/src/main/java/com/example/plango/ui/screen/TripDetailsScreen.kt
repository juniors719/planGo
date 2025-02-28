package com.example.plango.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plango.R
import com.example.plango.ui.components.button.PlanGoButton
import com.example.plango.ui.components.button.PlanGoButtonSecondary
import com.example.plango.ui.components.checkcard.SwipeTripChecklistItem
import com.example.plango.ui.components.dialog.AddChecklistItemDialog
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.Typography
import com.example.plango.utils.formatToDate
import com.example.plango.viewmodel.TripViewModel

@Composable
fun TripDetailsScreen(
    modifier: Modifier = Modifier,
    tripId: String?,
    navController: NavController,
    tripViewModel: TripViewModel
) {
    val trips by tripViewModel.trips.collectAsState() // Coleta as viagens do StateFlow corretamente
    val trip = trips.find { it.id == tripId }

    LaunchedEffect(tripId) {
        tripId?.let {
            tripViewModel.loadChecklist(it)
            tripViewModel.loadPackingChecklist(it)
        }
    }

    val checklists by tripViewModel.checklists.collectAsState()
    val packingChecklists by tripViewModel.packingChecklists.collectAsState()

    var showChecklistItemDialog by remember { mutableStateOf(false) }
    var showPackingChecklistItemDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
//            .padding(top = 16.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.3f),
            contentDescription = "Imagem da viagem",
            contentScale = ContentScale.Crop,
//            model = trip.imageUrl
            painter = painterResource(R.drawable.zermatt) // TODO: Substituir pela imagem do estabelecimento
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Column {
                        // Título
                        Spacer(modifier = Modifier.height(16.dp))
                        if (trip != null) {
                            Text(
                                text = trip.name ?: "Loading...",
                                style = Typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        // Descrição
                        if (trip != null) {
                            trip.description?.let {
                                Text(
                                    text = it,
                                    style = Typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        // Localização
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_map_pin),
                                contentDescription = "Localização",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            if (trip != null) {
                                trip.destination?.let {
                                    Text(
                                        text = it,
                                        style = Typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        // Data de início e fim
                        if (trip != null) {
                            TripDetailsDate(
                                dataInicio = trip.startDate.formatToDate(),
                                dataFim = trip.endDate.formatToDate()
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        // Checklist pré-viagem
                        Column(
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Checklist pré-viagem",
                                    style = Typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                PlanGoButton(
                                    iconRes = R.drawable.ic_add,
                                    onClick = { showChecklistItemDialog = true }
                                )
                                AddChecklistItemDialog(
                                    showDialog = showChecklistItemDialog,
                                    onDismiss = { showChecklistItemDialog = false },
                                    onAddItem = { newItem ->
                                        tripViewModel.createCheckListItem(
                                            tripId = tripId ?: "",
                                            title = newItem,
                                            isChecked = false,
                                            onSuccess = {
                                                showChecklistItemDialog = false
                                            }
                                        )
                                    }
                                )
                            }

                            if (checklists.isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    checklists.forEach { item ->
                                        val isChecked = remember { mutableStateOf(item.isChecked) }
                                        SwipeTripChecklistItem(
                                            itemName = item.title,
                                            isChecked = isChecked,
                                            onCheckedChange = {
                                                tripViewModel.updateCheckListItem(
                                                    item = item.copy(isChecked = it)
                                                )
                                            },
                                            onDelete = {
                                                tripViewModel.deleteCheckListItem(item)
                                            }
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = "Nenhum item no checklist",
                                    style = Typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            // Checklist pré-viagem
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Checklist de itens da mala",
                                    style = Typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                PlanGoButton(
                                    iconRes = R.drawable.ic_add,
                                    onClick = { showPackingChecklistItemDialog = true }
                                )
                                AddChecklistItemDialog(
                                    showDialog = showPackingChecklistItemDialog,
                                    onDismiss = { showPackingChecklistItemDialog = false },
                                    onAddItem = { newItem ->
                                        tripViewModel.createPackingCheckListItem(
                                            tripId = tripId ?: "",
                                            title = newItem,
                                            isChecked = false,
                                            onSuccess = {
                                                showPackingChecklistItemDialog = false
                                            }
                                        )
                                    }
                                )
                            }
                            if (packingChecklists.isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    packingChecklists.forEach { item ->
                                        val isChecked = remember { mutableStateOf(item.isChecked) }
                                        SwipeTripChecklistItem(
                                            itemName = item.title,
                                            isChecked = isChecked,
                                            onCheckedChange = {
                                                tripViewModel.updatePackingCheckListItem(
                                                    item = item.copy(isChecked = it)
                                                )
                                            },
                                            onDelete = {
                                                tripViewModel.deletePackingCheckListItem(item)
                                            }
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = "Nenhum item no checklist",
                                    style = Typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PlanGoButton(
                        iconRes = R.drawable.ic_edit,
                        onClick = {
//                    navController.navigate(Routes.EDIT_TRIP + "/$tripId")
                        },
                        text = "Editar",
                        modifier = Modifier.weight(1f)
                    )
                    PlanGoButtonSecondary(
                        iconRes = R.drawable.ic_delete,
                        onClick = {
//                    tripViewModel.deleteTrip(tripId ?: "")
//                    navController.navigate(Routes.HOME)
                        },
                        text = "Deletar",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        PlanGoButtonSecondary(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 48.dp, start = 16.dp),
            iconRes = R.drawable.ic_arrow_left,
            onClick = {
                navController.navigate(Routes.HOME)
            }
        )

    }
}

@Composable
fun TripDetailsDate(modifier: Modifier = Modifier, dataInicio: String, dataFim: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Ícone calendário",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "$dataInicio ~ $dataFim",
            style = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

//@Preview
//@Composable
//private fun TripDetailsScreenPreview() {
//    PlanGoTheme {
//        TripDetailsScreen(tripId = "1")
//    }
//}