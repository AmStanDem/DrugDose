package it.uninsubria.drugdose.data.repository

import it.uninsubria.drugdose.data.local.dao.DrugDao
import it.uninsubria.drugdose.data.mapper.toDomain
import it.uninsubria.drugdose.domain.model.Drug
import it.uninsubria.drugdose.domain.repository.DrugRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DrugRepositoryImpl(
    private val dao: DrugDao
) : DrugRepository {
    override fun getDrugs(): Flow<List<Drug>> {
        return dao.getDrugs().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getDrugById(id: String): Drug? {
        return dao.getDrugById(id)?.toDomain()
    }
}