package it.uninsubria.drugdose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import it.uninsubria.drugdose.data.local.entity.DrugEntity
import it.uninsubria.drugdose.data.local.dao.DrugDao

@Database(entities = [DrugEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class DrugDatabase : RoomDatabase() {
    abstract fun drugDao(): DrugDao
}