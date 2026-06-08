package it.uninsubria.drugdose.domain.model

import kotlinx.serialization.Serializable

/**
 * Rappresenta una fascia di peso con la relativa dose associata.
 *
 * Utilizzata per i farmaci con strategia [FormulaType.WEIGHT_RANGE].
 *
 * @property minKg Peso minimo della fascia (incluso).
 * @property maxKg Peso massimo della fascia (incluso).
 * @property dose Dose fissa da somministrare per questa fascia.
 * @author Thomas Riotto
 */
@Serializable
data class WeightRange(
    val minKg: Double,
    val maxKg: Double,
    val dose: Double
)
