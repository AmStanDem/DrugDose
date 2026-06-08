package it.uninsubria.drugdose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import it.uninsubria.drugdose.ui.navigation.NavGraph
import it.uninsubria.drugdose.ui.theme.DrugDoseTheme

@AndroidEntryPoint // FONDAMENTALE: permette a Hilt di funzionare in tutta l'app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Abilita la visualizzazione a tutto schermo (sotto la barra di stato)
        enableEdgeToEdge()

        setContent {
            DrugDoseTheme {
                // Inizializziamo il controller della navigazione
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Usiamo un Box per applicare il padding del sistema (status bar/nav bar)
                    // e carichiamo il nostro NavGraph
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}