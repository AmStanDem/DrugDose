package it.uninsubria.drugdose.domain.usecase

import it.uninsubria.drugdose.domain.model.Drug
import it.uninsubria.drugdose.domain.model.FormulaType
import it.uninsubria.drugdose.domain.model.Patient
import kotlin.math.sqrt
import javax.inject.Inject

/**
 * Use Case responsabile del calcolo della dose teorica totale.
 *
 * Applica la formula matematica appropriata in base al [FormulaType] definito nel [Drug].
 * Include il calcolo della superficie corporea (BSA) tramite la formula di Mosteller.
 *
 * ### Formule Applicate
 * - **Per Peso**: $$ Dose_{tot} = Dose_{unit} \times Peso_{kg} $$
 * - **Per BSA**: $$ Dose_{tot} = Dose_{unit} \times \sqrt{\frac{Altezza_{cm} \times Peso_{kg}}{3600}} $$
 *
 * @author Thomas Riotto
 */
class CalculateDoseUseCase @Inject constructor() {

    /**
     * Esegue il calcolo della dose in base ai parametri del paziente e del farmaco.
     *
     * @param patient I dati biometrici del paziente.
     * @param drug Il farmaco con le relative regole di dosaggio.
     * @return Il valore numerico della dose calcolata. Restituisce 0.0 se i parametri necessari sono assenti.
     */
    operator fun invoke(patient: Patient, drug: Drug): Double {

        drug.minAgeYears?.let { minAge ->
            val patientAge = patient.ageYears ?: 0
            if (patientAge < minAge) {
                throw IllegalArgumentException("Attenzione: farmaco non indicato sotto i $minAge anni.")
            }
        }

        drug.minWeightKg?.let { minWeight ->
            val patientWeight = patient.weightKg ?: 0.0
            if (patientWeight < minWeight) {
                throw IllegalArgumentException("Il peso ($patientWeight kg) è inferiore al minimo richiesto di $minWeight kg.")
            }
        }

        return when (drug.formulaType) {
            FormulaType.PER_KG -> {
                val weight = patient.weightKg ?: 0.0
                drug.unitDose * weight
            }

            FormulaType.PER_BSA -> {
                val weight = patient.weightKg ?: 0.0
                val height = patient.heightCm ?: 0.0
                if (weight > 0 && height > 0) {
                    calculateBsa(height, weight) * drug.unitDose
                } else 0.0
            }

            FormulaType.FIXED -> drug.unitDose

            FormulaType.WEIGHT_RANGE -> {
                val weight = patient.weightKg ?: 0.0
                val range = drug.weightRanges.find { weight >= it.minKg && weight <= it.maxKg }
                range?.dose ?: 0.0
            }
        }
    }

    /**
     * Calcola la superficie corporea (Body Surface Area) usando la formula di Mosteller.
     *
     * @param heightCm Altezza in centimetri.
     * @param weightKg Peso in chilogrammi.
     * @return Il valore della BSA in m².
     */
    private fun calculateBsa(heightCm: Double, weightKg: Double): Double {
        return sqrt((heightCm * weightKg) / 3600.0)
    }
}
