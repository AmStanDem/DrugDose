package it.uninsubria.drugdose.domain.model

import kotlinx.serialization.Serializable

/**
 * Rappresenta un farmaco e le relative regole terapeutiche per il calcolo del dosaggio.
 *
 * Questa classe contiene non solo i dati anagrafici del farmaco, ma anche i vincoli
 * di sicurezza necessari per la validazione clinica.
 *
 * @property id Identificativo univoco (es. UUID o codice AIC).
 * @property name Nome del principio attivo o nome commerciale.
 * @property indication Breve descrizione dell'indicazione clinica.
 * @property formulaType La strategia di calcolo da applicare.
 * @property unitDose Il valore numerico della dose unitaria.
 * @property unit L'unità di misura della dose.
 * @property maxDose La dose massima totale consentita.
 * @property minWeightKg Il peso minimo del paziente richiesto.
 * @property minAgeYears L'età minima del paziente richiesta.
 * @property source La fonte medica o regolatoria del dosaggio.
 * @property alerts Elenco di avvisi o controindicazioni.
 * @property weightRanges Fasce di peso per la strategia WEIGHT_RANGE.
 * @author Thomas Riotto
 */
@Serializable
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
    val alerts: List<String> = emptyList(),
    val weightRanges: List<WeightRange> = emptyList()
)
