package it.uninsubria.drugdose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import it.uninsubria.drugdose.R
import it.uninsubria.drugdose.ui.viewmodel.CalculationStep

/**
 * Indicatore visivo del progresso del calcolo della dose.
 * Mostra una serie di cerchi numerati o con icone che rappresentano gli step:
 * Farmaco -> Dati Paziente -> Risultato.
 *
 * @param currentStep Lo step attuale nel processo di calcolo.
 * @author Thomas Riotto
 */
@Composable
fun StepIndicator(currentStep: CalculationStep) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StepCircle(
            label = stringResource(R.string.step_drug), 
            active = currentStep == CalculationStep.DRUG_SELECTION, 
            completed = currentStep.ordinal > 0
        )
        HorizontalDivider(modifier = Modifier.weight(1f).padding(horizontal = 8.dp))
        StepCircle(
            label = stringResource(R.string.step_patient), 
            active = currentStep == CalculationStep.PATIENT_DATA, 
            completed = currentStep.ordinal > 1
        )
        HorizontalDivider(modifier = Modifier.weight(1f).padding(horizontal = 8.dp))
        StepCircle(
            label = stringResource(R.string.step_result), 
            active = currentStep == CalculationStep.RESULT, 
            completed = false
        )
    }
}

/**
 * Singolo elemento circolare dell'indicatore di step.
 * Cambia colore in base allo stato (attivo, completato o inattivo).
 *
 * @param label Etichetta testuale dello step.
 * @param active Vero se lo step è quello attualmente visualizzato.
 * @param completed Vero se lo step è stato già completato.
 */
@Composable
private fun StepCircle(label: String, active: Boolean, completed: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = if (completed) Color(0xFF4CAF50) else if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (completed) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                } else {
                    Text(
                        text = if (active) "•" else "",
                        color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        Text(text = label, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
    }
}
