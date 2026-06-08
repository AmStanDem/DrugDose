package it.uninsubria.drugdose.ui.calculation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import it.uninsubria.drugdose.R
import it.uninsubria.drugdose.domain.model.Drug
import it.uninsubria.drugdose.domain.model.Patient
import it.uninsubria.drugdose.domain.repository.DrugRepository
import it.uninsubria.drugdose.domain.usecase.CalculateDoseUseCase
import it.uninsubria.drugdose.domain.usecase.GetDrugsUseCase
import it.uninsubria.drugdose.ui.navigation.DrugDetailRoute
import it.uninsubria.drugdose.ui.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDrugsUseCase: GetDrugsUseCase,
    private val calculateDoseUseCase: CalculateDoseUseCase,
    private val repository: DrugRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculationUiState())
    val uiState: StateFlow<CalculationUiState> = _uiState.asStateFlow()

    init {
        // Recupero l'ID del farmaco direttamente dalla rotta in modo type-safe
        val route = savedStateHandle.toRoute<DrugDetailRoute>()
        loadDrugById(route.drugId)
        loadDrugs()
    }

    private fun loadDrugById(id: String) {
        viewModelScope.launch {
            val drug = repository.getDrugById(id)
            drug?.let { onDrugSelected(it) }
        }
    }

    private fun loadDrugs() {
        viewModelScope.launch {
            getDrugsUseCase().collect { drugs ->
                _uiState.update { it.copy(drugs = drugs) }
            }
        }
    }

    fun onDrugSelected(drug: Drug) {
        _uiState.update { it.copy(
            selectedDrug = drug, 
            showResult = false,
            currentStep = CalculationStep.PATIENT_DATA
        ) }
    }

    fun onWeightChanged(weight: String) {
        // Accetta solo numeri positivi e virgola/punto decimale
        if (weight.isEmpty() || weight.matches(Regex("""^\d*[.,]?\d*$"""))) {
            _uiState.update { it.copy(weightInput = weight.replace(',', '.')) }
        }
    }

    fun onHeightChanged(height: String) {
        if (height.isEmpty() || height.matches(Regex("""^\d*[.,]?\d*$"""))) {
            _uiState.update { it.copy(heightInput = height.replace(',', '.')) }
        }
    }

    fun onAgeChanged(age: String) {
        if (age.isEmpty() || age.matches(Regex("""^\d*$"""))) {
            _uiState.update { it.copy(ageInput = age) }
        }
    }

    fun nextStep() {
        val currentState = _uiState.value
        when (currentState.currentStep) {
            CalculationStep.DRUG_SELECTION -> {
                if (currentState.selectedDrug != null) {
                    _uiState.update { it.copy(currentStep = CalculationStep.PATIENT_DATA) }
                }
            }
            CalculationStep.PATIENT_DATA -> {
                calculateDose()
            }
            CalculationStep.RESULT -> {}
        }
    }

    fun previousStep() {
        val currentState = _uiState.value
        when (currentState.currentStep) {
            CalculationStep.DRUG_SELECTION -> {}
            CalculationStep.PATIENT_DATA -> {
                _uiState.update { it.copy(currentStep = CalculationStep.DRUG_SELECTION) }
            }
            CalculationStep.RESULT -> {
                _uiState.update { it.copy(currentStep = CalculationStep.PATIENT_DATA, showResult = false) }
            }
        }
    }

    fun calculateDose() {
        val state = _uiState.value
        val drug = state.selectedDrug ?: return

        try {
            val patient = Patient(
                weightKg = state.weightInput.toDoubleOrNull(),
                heightCm = state.heightInput.toDoubleOrNull(),
                ageYears = state.ageInput.toIntOrNull()
            )

            val dose = calculateDoseUseCase(patient, drug)
            _uiState.update { 
                it.copy(
                    calculatedDose = dose, 
                    showResult = true,
                    currentStep = CalculationStep.RESULT,
                    error = null
                ) 
            }
        } catch (e: IllegalArgumentException) {
            _uiState.update { it.copy(error = UiText.DynamicString(e.message ?: "")) }
        } catch (e: Exception) {
            _uiState.update { it.copy(error = UiText.StringResource(R.string.error_calculation, e.message ?: "")) }
        }
    }
}
