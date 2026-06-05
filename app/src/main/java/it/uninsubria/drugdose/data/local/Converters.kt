package it.uninsubria.drugdose.data.local

import androidx.room.TypeConverter
import it.uninsubria.drugdose.domain.model.FormulaType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    // Usiamo il Json di Kotlinx Serialization che hai già nelle dipendenze
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringList(value: List<String>): String = json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String): List<String> = json.decodeFromString(value)

    @TypeConverter
    fun fromFormulaType(value: FormulaType): String = value.name

    @TypeConverter
    fun toFormulaType(value: String): FormulaType = FormulaType.valueOf(value)
}