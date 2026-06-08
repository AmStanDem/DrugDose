package it.uninsubria.drugdose.domain.usecase

import it.uninsubria.drugdose.domain.model.Drug
import it.uninsubria.drugdose.domain.repository.DrugRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use Case per recuperare la lista di tutti i farmaci disponibili dal repository.
 *
 * @property repository Il repository dei farmaci iniettato tramite Hilt.
 * @author Thomas Riotto
 */
class GetDrugsUseCase @Inject constructor(
    private val repository: DrugRepository
) {
    /**
     * Restituisce un [Flow] contenente la lista dei farmaci.
     */
    operator fun invoke(): Flow<List<Drug>> {
        return repository.getDrugs()
    }
}
