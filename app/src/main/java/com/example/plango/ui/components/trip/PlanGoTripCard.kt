package com.example.plango.ui.components.trip

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plango.R
import com.example.plango.data.model.Trip
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.Typography
import com.example.plango.utils.formatToDate

@Composable
fun PlanGoTripCard(
    modifier: Modifier = Modifier,
    trip: Trip,
    navController: NavController
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)),
        onClick = {
            // Rota que mostra os detalhes da trip
            navController.navigate(Routes.TRIP_DETAILS + "/${trip.id}")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.3f)
                    .height(IntrinsicSize.Min)
                    .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true),
                contentScale = ContentScale.Crop,
                painter = painterResource(R.drawable.zermatt), // TODO: Substituir pela imagem do estabelecimento
                contentDescription = "Imagem de capa do estabelecimento"
            )
            Column{
                // Título da viagem
                Text(text = trip.name ?: "Loading...", style = Typography.headlineSmall.copy(fontSize = 16.sp))

                Spacer(modifier = Modifier.height(12.dp))

                // Localização da viagem
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_map_pin),
                        contentDescription = "Localização",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    trip.destination?.let {
                        Text(
                            text = it,
                            style = Typography.bodyLarge
                        )
                    }
                }


                Spacer(modifier = Modifier.height(12.dp))

                // Data de início e fim da viagem
                Text(
                    text = "${trip.startDate.formatToDate()}  ~ ${trip.endDate.formatToDate()}",
                    style = Typography.bodyMedium
                )
            }
        }
    }
}