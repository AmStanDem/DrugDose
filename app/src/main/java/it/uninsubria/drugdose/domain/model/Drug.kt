package it.uninsubria.drugdose.domain.model

/**
 * Rappresenta un farmaco e le relative regole terapeutiche per il calcolo del dosaggio.
 * ...
 * @author Thomas Riotto
 */
data class Drug(
    val id: String,
    val name: String,
    val indication: String,
    val formulaType: FormulaType,
    val unitDose: Double,
    val unit: String,
    val maxDose: Double? = null,
    val minWeightKg: Double? = null,
    val minAgeYears: Int? = null,
    val source: String? = null,
    val alerts: List<String> = emptyList()
)