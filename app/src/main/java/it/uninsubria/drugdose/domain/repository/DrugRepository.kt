package it.uninsubria.drugdose.domain.repository

import it.uninsubria.drugdose.domain.model.Drug
import kotlinx.coroutines.flow.Flow

/**
 * Interfaccia per la gestione dei dati dei farmaci.
 *
 * Segue il Repository Pattern per isolare il dominio dalla sorgente dati reale.
 */
interface DrugRepository {
    /** Recupera tutti i farmaci disponibili. */
    fun getDrugs(): Flow<List<Drug>>

    /** Recupera un farmaco specifico tramite il suo ID. */
    suspend fun getDrugById(id: String): Drug?
}