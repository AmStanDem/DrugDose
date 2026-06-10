package it.uninsubria.drugdose.domain.repository

import it.uninsubria.drugdose.domain.model.Drug
import kotlinx.coroutines.flow.Flow

/**
 * Interfaccia del Repository per la gestione dei dati dei farmaci.
 * Definisce il contratto per l'accesso ai dati, astraendo la sorgente (DB, API, ecc.).
 *
 * @author Thomas Riotto
 */
interface DrugRepository {
    /**
     * Fornisce un flusso continuo della lista di farmaci disponibili.
     */
    fun getDrugs(): Flow<List<Drug>>

    /**
     * Recupera i dettagli di un singolo farmaco in base all'ID.
     * @param id Identificativo univoco del farmaco.
     * @return Il modello [Drug] o null se non esiste.
     */
    suspend fun getDrugById(id: String): Drug?
}
