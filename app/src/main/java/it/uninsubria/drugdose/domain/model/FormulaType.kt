package it.uninsubria.drugdose.domain.model

/**
 * Definisce le modalità di calcolo del dosaggio supportate dal sistema DrugDose.
 *
 * Questa enumerazione rappresenta le diverse strategie cliniche utilizzate per
 * determinare la dose corretta in base ai parametri del paziente.
 *
 * @author Thomas Riotto
 */
enum class FormulaType {
    /**
     * Calcolo basato sul peso corporeo del paziente.
     * Esempio: 200 μg/kg o 15 mg/kg.
     * Richiede che [Patient.weightKg] sia valorizzato.
     */
    PER_KG,

    /**
     * Calcolo basato sulla superficie corporea (Body Surface Area - BSA).
     * Esempio: 50 mg/m².
     * Richiede che [Patient.weightKg] e [Patient.heightCm] siano valorizzati per applicare
     * la formula di Mosteller.
     */
    PER_BSA,

    /**
     * Dosaggio fisso indipendente dai parametri biometrici del paziente.
     * Esempio: 1 compressa, 1 bustina o una dose standard per adulti.
     */
    FIXED,

    /**
     * Dosaggio basato su tabelle a intervalli o fasce di peso.
     * Utilizzato quando la dose non è lineare ma varia per scaglioni di peso.
     */
    WEIGHT_RANGE
}
