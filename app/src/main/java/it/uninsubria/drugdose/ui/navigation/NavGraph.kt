package it.uninsubria.drugdose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import it.uninsubria.drugdose.ui.calculation.CalculationViewModel
import it.uninsubria.drugdose.ui.screens.CalculationScreen
import it.uninsubria.drugdose.ui.screens.DrugListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = DrugListRoute
    ) {
        // Schermata Lista Farmaci
        composable<DrugListRoute> {
            DrugListScreen(
                onNavigateToDetail = { id ->
                    navController.navigate(DrugDetailRoute(id))
                }
            )
        }

        // Schermata Dettaglio Farmaco (Calcolatore)
        composable<DrugDetailRoute> {
            val viewModel: CalculationViewModel = hiltViewModel()
            CalculationScreen(viewModel = viewModel)
        }
    }
}
