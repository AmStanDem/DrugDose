package it.uninsubria.drugdose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Componente generico per visualizzare uno stato vuoto quando non ci sono dati.
 * Utile per informare l'utente che una ricerca o una lista non ha prodotto risultati.
 *
 * @param title Titolo principale del messaggio di stato vuoto.
 * @param description Sottotitolo descrittivo per fornire contesto o istruzioni.
 * @author Thomas Riotto
 */
@Composable
fun EmptyStateMessage(
    title: String = "Nessun dato trovato",
    description: String = "L'elenco è attualmente vuoto."
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
