package it.uninsubria.drugdose.ui.calculation

import it.uninsubria.drugdose.domain.model.Drug
import it.uninsubria.drugdose.ui.util.UiText

/**
 * Rappresenta lo stato dell'interfaccia utente per la schermata di calcolo.
 *
 * @property drugs Lista di tutti i farmaci caricati.
 * @property selectedDrug Il farmaco attualmente selezionato dall'utente.
 * @property weightInput Input testuale per il peso (gestito come String per la UI).
 * @property heightInput Input testuale per l'altezza.
 * @property ageInput Input testuale per l'età.
 * @property calculatedDose Risultato del calcolo della dose.
 * @property showResult Flag per mostrare/nascondere la card del risultato.
 * @property error Errore di validazione o di sistema da mostrare (internazionalizzabile).
 */
data class CalculationUiState(
    val drugs: List<Drug> = emptyList(),
    val selectedDrug: Drug? = null,
    val weightInput: String = "",
    val heightInput: String = "",
    val ageInput: String = "",
    val calculatedDose: Double? = null,
    val showResult: Boolean = false,
    val error: UiText? = null,
    val currentStep: CalculationStep = CalculationStep.DRUG_SELECTION
)

enum class CalculationStep {
    DRUG_SELECTION,
    PATIENT_DATA,
    RESULT
}
