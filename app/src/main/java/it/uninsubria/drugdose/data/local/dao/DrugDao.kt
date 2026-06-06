package it.uninsubria.drugdose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.uninsubria.drugdose.data.local.entity.DrugEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugDao {
    @Query("SELECT * FROM drugs")
    fun getDrugs(): Flow<List<DrugEntity>>

    @Query("SELECT * FROM drugs WHERE id = :id")
    suspend fun getDrugById(id: String): DrugEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrugs(drugs: List<DrugEntity>)
}