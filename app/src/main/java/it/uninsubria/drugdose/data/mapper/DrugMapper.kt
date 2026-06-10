package it.uninsubria.drugdose.data.mapper

import it.uninsubria.drugdose.data.local.entity.DrugEntity
import it.uninsubria.drugdose.domain.model.Drug

/**
 * Mapper responsabile della conversione tra entità di database (Data Layer)
 * e modelli di dominio (Domain Layer).
 *
 * Assicura il disaccoppiamento tra la struttura del database Room e la logica di business.
 *
 * @author Thomas Riotto
 */
fun DrugEntity.toDomain(): Drug {
    return Drug(
        id = id,
        name = name,
        indication = indication,
        formulaType = formulaType,
        unitDose = unitDose,
        unit = unit,
        maxDose = maxDose,
        minWeightKg = minWeightKg,
        minAgeYears = minAgeYears,
        source = source,
        alerts = alerts,
        weightRanges = weightRanges
    )
}

/**
 * Converte un modello di dominio in un'entità per la persistenza su database.
 */
fun Drug.toEntity(): DrugEntity {
    return DrugEntity(
        id = id,
        name = name,
        indication = indication,
        formulaType = formulaType,
        unitDose = unitDose,
        unit = unit,
        maxDose = maxDose,
        minWeightKg = minWeightKg,
        minAgeYears = minAgeYears,
        source = source,
        alerts = alerts,
        weightRanges = weightRanges
    )
}
