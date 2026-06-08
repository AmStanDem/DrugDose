package it.uninsubria.drugdose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import it.uninsubria.drugdose.domain.model.FormulaType
import it.uninsubria.drugdose.domain.model.WeightRange
import kotlinx.serialization.Serializable


@Entity(tableName = "drugs")
@Serializable
data class DrugEntity(
    @PrimaryKey val id: String,
    val name: String,
    val indication: String,
    val formulaType: FormulaType,
    val unitDose: Double,
    val unit: String,
    val maxDose: Double? = null,
    val minWeightKg: Double? = null,
    val minAgeYears: Int? = null,
    val source: String? = null,
    val alerts: List<String> = emptyList(),
    val weightRanges: List<WeightRange> = emptyList()
)
