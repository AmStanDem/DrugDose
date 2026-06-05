package it.uninsubria.drugdose.domain.model

/**
 * Rappresenta i dati biometrici di un paziente necessari per il calcolo del dosaggio.
 *
 * @property weightKg Il peso del paziente in chilogrammi (fondamentale per dosi mg/kg).
 * @property heightCm L'altezza del paziente in centimetri (necessaria per il calcolo della BSA).
 * @property ageYears L'età del paziente in anni (utile per vincoli e alert clinici).
 */
data class Patient(
    val weightKg: Double? = null,
    val heightCm: Double? = null,
    val ageYears: Int? = null
)