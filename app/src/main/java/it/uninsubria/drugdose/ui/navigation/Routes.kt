package it.uninsubria.drugdose.ui.navigation

import kotlinx.serialization.Serializable

// Definiamo le destinazioni come classi/oggetti serializzabili
@Serializable
object DrugListRoute

@Serializable
data class DrugDetailRoute(val drugId: String)
