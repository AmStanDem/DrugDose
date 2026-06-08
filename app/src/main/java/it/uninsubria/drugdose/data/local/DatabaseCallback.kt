package it.uninsubria.drugdose.data.local

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import it.uninsubria.drugdose.data.local.entity.DrugEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Provider

/**
 * Callback per popolare il database Room con i dati iniziali dal file JSON.
 */
class DatabaseCallback(
    private val context: Context,
    private val daoProvider: Provider<it.uninsubria.drugdose.data.local.dao.DrugDao>,
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.d("DrugDose", "Database onCreate triggered")
        scope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        Log.d("DrugDose", "Database onOpen triggered")
        // Forza il controllo del popolamento anche all'apertura per debug
        scope.launch(Dispatchers.IO) {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        try {
            val dao = daoProvider.get()
            // Controlliamo se è già popolato
            // Nota: getDrugs() restituisce un Flow, quindi usiamo un metodo sospeso o check alternativo
            // Per semplicità, proviamo l'inserimento: Room gestirà i conflitti se gli ID sono uguali
            Log.d("DrugDose", "Attempting to populate database from JSON...")
            
            val jsonString = context.assets.open("drugs.json").bufferedReader().use { it.readText() }
            val json = Json { ignoreUnknownKeys = true }
            val drugs: List<DrugEntity> = json.decodeFromString(jsonString)
            
            dao.insertDrugs(drugs)
            Log.d("DrugDose", "Database successfully populated with ${drugs.size} drugs")
        } catch (e: Exception) {
            Log.e("DrugDose", "Error populating database", e)
        }
    }
}
