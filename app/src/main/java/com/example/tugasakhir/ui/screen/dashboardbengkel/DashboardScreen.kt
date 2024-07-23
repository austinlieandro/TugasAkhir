package com.example.tugasakhir.ui.screen.dashboardbengkel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.JenisLayananScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KarayawanScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ListOperasionalScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ReservasiBengkelScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateBengkelScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun DasbboardScreen(
    idBengkel: Int,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
){
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Dasbor Bengkel",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navigator.navigate(ReservasiBengkelScreenDestination(idBengkel))
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.History,
                    contentDescription = "Icon Reservasi Bengkel",
                    tint = colorScheme.onSurface,
                    modifier = modifier
                        .padding(0.dp, 10.dp)
                )
                Text(
                    text = "Panel Reservasi",
                    color = colorScheme.onSurface,
                    modifier = modifier
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Filled.NavigateNext,
                        contentDescription = "Navigate to Panel Reservasi",
                        tint = colorScheme.onSurface,
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navigator.navigate(UpdateBengkelScreenDestination)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Icon Bengkel",
                    tint = colorScheme.onSurface,
                    modifier = modifier
                        .padding(0.dp, 10.dp)
                )
                Text(
                    text = "Edit Bengkel",
                    color = colorScheme.onSurface,
                    modifier = modifier
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Filled.NavigateNext,
                        contentDescription = "Navigate to Edit Bengkel",
                        tint = colorScheme.onSurface,
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navigator.navigate(KarayawanScreenDestination(idBengkel))
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Icon Karyawan",
                    tint = colorScheme.onSurface,
                    modifier = modifier
                        .padding(0.dp, 10.dp)
                )
                Text(
                    text = "Edit/Nambah Karyawan",
                    color = colorScheme.onSurface,
                    modifier = modifier
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Filled.NavigateNext,
                        contentDescription = "Navigate to Karyawan",
                        tint = colorScheme.onSurface,
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navigator.navigate(ListOperasionalScreenDestination)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Alarm,
                    contentDescription = "Icon Jam Operasional",
                    tint = colorScheme.onSurface,
                    modifier = modifier
                        .padding(0.dp, 10.dp)
                )
                Text(
                    text = "Edit Jam Operasional",
                    color = colorScheme.onSurface,
                    modifier = modifier
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Filled.NavigateNext,
                        contentDescription = "Navigate to Jam operasional",
                        tint = colorScheme.onSurface,
                    )
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        navigator.navigate(JenisLayananScreenDestination("lainnya", idBengkel ))
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.MiscellaneousServices,
                    contentDescription = "Icon Jam Operasional",
                    tint = colorScheme.onSurface,
                    modifier = modifier
                        .padding(0.dp, 10.dp)
                )
                Text(
                    text = "Edit/Nambah Jenis Layanan",
                    color = colorScheme.onSurface,
                    modifier = modifier
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Filled.NavigateNext,
                        contentDescription = "Navigate to Jenis Layanan",
                        tint = colorScheme.onSurface,
                    )
                }
            }
        }
    }
}