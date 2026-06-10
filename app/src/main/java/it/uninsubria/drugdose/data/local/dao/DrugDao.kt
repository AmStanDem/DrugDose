package it.uninsubria.drugdose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.uninsubria.drugdose.data.local.entity.DrugEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) per la gestione della persistenza dei farmaci.
 * Fornisce i metodi per interagire con la tabella 'drugs' del database Room.
 *
 * @author Thomas Riotto
 */
@Dao
interface DrugDao {
    /**
     * Recupera tutti i farmaci presenti nel database.
     * @return Un [Flow] contenente la lista degli [DrugEntity].
     */
    @Query("SELECT * FROM drugs")
    fun getDrugs(): Flow<List<DrugEntity>>

    /**
     * Recupera un farmaco specifico tramite il suo identificativo.
     * @param id L'ID del farmaco da cercare.
     * @return L'entità [DrugEntity] corrispondente, o null se non trovata.
     */
    @Query("SELECT * FROM drugs WHERE id = :id")
    suspend fun getDrugById(id: String): DrugEntity?

    /**
     * Inserisce una lista di farmaci nel database.
     * In caso di conflitto (stesso ID), sostituisce i dati esistenti.
     *
     * @param drugs La lista di entità da inserire.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrugs(drugs: List<DrugEntity>)
}
