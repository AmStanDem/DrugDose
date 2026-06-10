package it.uninsubria.drugdose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.uninsubria.drugdose.R
import it.uninsubria.drugdose.ui.components.DrugCard
import it.uninsubria.drugdose.ui.components.EmptyStateMessage
import it.uninsubria.drugdose.ui.viewmodel.DrugListViewModel

/**
 * Schermata dell'elenco dei farmaci disponibili nel database.
 * Rappresenta il punto di ingresso principale dell'applicazione.
 *
 * @param onNavigateToDetail Callback per navigare alla schermata di calcolo per un farmaco specifico.
 * @param viewModel Il ViewModel che gestisce il caricamento e lo stato della lista dei farmaci.
 * @author Thomas Riotto
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugListScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: DrugListViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel<DrugListViewModel>()
) {
    val drugs by viewModel.drugs.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (drugs.isEmpty()) {
                EmptyStateMessage(
                    title = stringResource(R.string.error_no_drug_found),
                    description = stringResource(R.string.error_db_empty)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(drugs) { drug ->
                        DrugCard(
                            drug = drug,
                            onClick = { onNavigateToDetail(drug.id) }
                        )
                    }
                }
            }
        }
    }
}
