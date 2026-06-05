package it.uninsubria.drugdose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import it.uninsubria.drugdose.domain.model.FormulaType


@Entity(tableName = "drugs")
data class DrugEntity(
    @PrimaryKey val id: String,
    val name: String,
    val indication: String,
    val formulaType: FormulaType, // Ora puoi usare l'Enum direttamente!
    val unitDose: Double,
    val unit: String,
    val maxDose: Double?,
    val minWeightKg: Double?,
    val minAgeYears: Int?,
    val source: String?,
    val alerts: List<String> // Ora puoi usare la List direttamente!
)