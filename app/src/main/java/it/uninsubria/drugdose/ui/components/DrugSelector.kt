package it.uninsubria.drugdose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import it.uninsubria.drugdose.R
import it.uninsubria.drugdose.domain.model.Drug

/**
 * Componente di selezione per la scelta di un farmaco dalla lista disponibile.
 * Utilizza un menu a tendina (Exposed Dropdown Menu) conforme agli standard Material 3.
 *
 * @param drugs La lista dei farmaci caricati dal database.
 * @param selectedDrug Il farmaco attualmente selezionato, o null se nessuno è scelto.
 * @param onDrugSelected Callback invocata quando l'utente seleziona un farmaco.
 * @author Thomas Riotto
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugSelector(
    drugs: List<Drug>,
    selectedDrug: Drug?,
    onDrugSelected: (Drug) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedDrug?.name ?: stringResource(R.string.drug_selector_placeholder),
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            drugs.forEach { drug ->
                DropdownMenuItem(
                    text = { Text(drug.name) },
                    onClick = {
                        onDrugSelected(drug)
                        expanded = false
                    }
                )
            }
        }
    }
}
