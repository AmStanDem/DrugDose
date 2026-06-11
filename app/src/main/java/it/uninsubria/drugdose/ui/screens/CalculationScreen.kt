package it.uninsubria.drugdose.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.uninsubria.drugdose.R
import it.uninsubria.drugdose.domain.model.Drug
import it.uninsubria.drugdose.domain.model.FormulaType
import it.uninsubria.drugdose.ui.viewmodel.CalculationStep
import it.uninsubria.drugdose.ui.viewmodel.CalculationUiState
import it.uninsubria.drugdose.ui.viewmodel.CalculationViewModel
import it.uninsubria.drugdose.ui.components.DrugSelector
import it.uninsubria.drugdose.ui.components.SafetyAlertCard
import it.uninsubria.drugdose.ui.components.StepIndicator

/**
 * Schermata principale del processo di calcolo della dose.
 * Gestisce l'integrazione con il ViewModel e delega la visualizzazione al contenuto stateless.
 *
 * @param viewModel Il ViewModel che mantiene lo stato del calcolo.
 * @author Thomas Riotto
 */
@Composable
fun CalculationScreen(
    viewModel: CalculationViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalculationContent(
        uiState = uiState,
        onNextStep = viewModel::nextStep,
        onPreviousStep = viewModel::previousStep,
        onDrugSelected = viewModel::onDrugSelected,
        onWeightChanged = viewModel::onWeightChanged,
        onHeightChanged = viewModel::onHeightChanged,
        onAgeChanged = viewModel::onAgeChanged
    )
}

/**
 * Contenuto stateless della schermata di calcolo.
 * Implementa il pattern State Hoisting per facilitare test e preview.
 *
 * @param uiState Lo stato corrente della UI.
 * @param onNextStep Callback per passare allo step successivo.
 * @param onPreviousStep Callback per tornare allo step precedente.
 * @param onDrugSelected Callback per la selezione di un farmaco.
 * @param onWeightChanged Callback per la modifica del peso.
 * @param onHeightChanged Callback per la modifica dell'altezza.
 * @param onAgeChanged Callback per la modifica dell'età.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculationContent(
    uiState: CalculationUiState,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onDrugSelected: (Drug) -> Unit,
    onWeightChanged: (String) -> Unit,
    onHeightChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.calc_title), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (uiState.currentStep != CalculationStep.DRUG_SELECTION) {
                        IconButton(onClick = onPreviousStep) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.calc_back))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            if (uiState.currentStep != CalculationStep.RESULT) {
                BottomAppBar(
                    containerColor = Color.Transparent,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Button(
                        onClick = onNextStep,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = when (uiState.currentStep) {
                            CalculationStep.DRUG_SELECTION -> uiState.selectedDrug != null
                            CalculationStep.PATIENT_DATA -> uiState.weightInput.isNotEmpty()
                        }
                    ) {
                        Text(if (uiState.currentStep == CalculationStep.PATIENT_DATA) stringResource(R.string.calc_calculate) else stringResource(R.string.calc_next))
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            if (uiState.currentStep == CalculationStep.PATIENT_DATA) Icons.Default.Check else Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StepIndicator(currentStep = uiState.currentStep)

            AnimatedContent(
                targetState = uiState.currentStep,
                transitionSpec = {
                    if (targetState.ordinal > initialState.ordinal) {
                        slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
                    }.using(SizeTransform(clip = false))
                },
                label = "StepTransition"
            ) { step ->
                when (step) {
                    CalculationStep.DRUG_SELECTION -> {
                        DrugSelectionStep(
                            drugs = uiState.drugs,
                            selectedDrug = uiState.selectedDrug,
                            onDrugSelected = onDrugSelected
                        )
                    }
                    CalculationStep.PATIENT_DATA -> {
                        PatientDataStep(
                            weight = uiState.weightInput,
                            height = uiState.heightInput,
                            age = uiState.ageInput,
                            formulaType = uiState.selectedDrug?.formulaType ?: FormulaType.PER_KG,
                            onWeightChanged = onWeightChanged,
                            onHeightChanged = onHeightChanged,
                            onAgeChanged = onAgeChanged
                        )
                    }
                    CalculationStep.RESULT -> {
                        ResultStep(
                            dose = uiState.calculatedDose ?: 0.0,
                            unit = uiState.selectedDrug?.unit ?: "",
                            alerts = uiState.selectedDrug?.alerts ?: emptyList(),
                            onReset = onPreviousStep
                        )
                    }
                }
            }

            uiState.error?.let {
                Text(text = it.asString(), color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun DrugSelectionStep(
    drugs: List<Drug>,
    selectedDrug: Drug?,
    onDrugSelected: (Drug) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(stringResource(R.string.drug_selection_prompt), style = MaterialTheme.typography.titleMedium)
        DrugSelector(
            drugs = drugs,
            selectedDrug = selectedDrug,
            onDrugSelected = onDrugSelected
        )
        selectedDrug?.let { drug ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f))
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(drug.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(drug.indication, style = MaterialTheme.typography.bodySmall)
                    
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f))
                    
                    // --- Informazioni Formula e Vincoli (Allineamento al Mockup) ---
                    val formulaDesc = when(drug.formulaType) {
                        FormulaType.PER_KG -> stringResource(R.string.formula_per_kg)
                        FormulaType.PER_BSA -> stringResource(R.string.formula_per_bsa)
                        FormulaType.FIXED -> stringResource(R.string.formula_fixed)
                        FormulaType.WEIGHT_RANGE -> stringResource(R.string.formula_weight_range)
                    }
                    
                    Text(
                        text = stringResource(R.string.info_formula_label, formulaDesc),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    if (drug.minAgeYears != null || drug.minWeightKg != null) {
                        Column {
                            Text(
                                text = stringResource(R.string.info_constraints_label),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                            drug.minAgeYears?.let {
                                Text("• " + stringResource(R.string.constraint_min_age, it), style = MaterialTheme.typography.labelSmall)
                            }
                            drug.minWeightKg?.let {
                                Text("• " + stringResource(R.string.constraint_min_weight, it), style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PatientDataStep(
    weight: String,
    height: String,
    age: String,
    formulaType: FormulaType,
    onWeightChanged: (String) -> Unit,
    onHeightChanged: (String) -> Unit,
    onAgeChanged: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(stringResource(R.string.patient_data_title), style = MaterialTheme.typography.titleMedium)
        
        OutlinedTextField(
            value = weight,
            onValueChange = onWeightChanged,
            label = { Text(stringResource(R.string.patient_weight_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            prefix = { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
        )

        if (formulaType == FormulaType.PER_BSA) {
            OutlinedTextField(
                value = height,
                onValueChange = onHeightChanged,
                label = { Text(stringResource(R.string.patient_height_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = age,
            onValueChange = onAgeChanged,
            label = { Text(stringResource(R.string.patient_age_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ResultStep(
    dose: Double,
    unit: String,
    alerts: List<String>,
    onReset: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val locale = configuration.locales[0]

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.result_total_dose), style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${String.format(locale, "%.2f", dose)} $unit",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF2E7D32)
                )
            }
        }

        if (alerts.isNotEmpty()) {
            SafetyAlertCard(alerts = alerts)
        }

        OutlinedButton(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.calc_edit_data))
        }
    }
}
