package it.uninsubria.drugdose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.uninsubria.drugdose.domain.model.Drug
import it.uninsubria.drugdose.domain.repository.DrugRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DrugListViewModel @Inject constructor(
    private val repository: DrugRepository
) : ViewModel() {

    // Recupera i farmaci dal repository e li trasforma in uno StateFlow
    // che la UI di Compose può "osservare"
    val drugs: StateFlow<List<Drug>> = repository.getDrugs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Ottimizza la memoria
            initialValue = emptyList()
        )
}