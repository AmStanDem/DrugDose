package it.uninsubria.drugdose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.ui.res.stringResource
import it.uninsubria.drugdose.R

@Composable
fun SafetyAlertCard(alerts: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("⚠️", modifier = Modifier.padding(end = 8.dp))
                Text(stringResource(R.string.safety_alerts_title), fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
            }
            Spacer(Modifier.height(8.dp))
            alerts.forEach { alert ->
                Text("• $alert", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
