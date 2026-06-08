package it.uninsubria.drugdose.data.mapper

import it.uninsubria.drugdose.data.local.entity.DrugEntity
import it.uninsubria.drugdose.domain.model.Drug

// Trasforma Entity -> Domain
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

// Trasforma Domain -> Entity (utile per il salvataggio)
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
