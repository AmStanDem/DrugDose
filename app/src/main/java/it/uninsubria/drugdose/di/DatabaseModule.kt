package it.uninsubria.drugdose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.uninsubria.drugdose.data.local.DrugDatabase
import it.uninsubria.drugdose.data.local.dao.DrugDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DrugDatabase {
        return Room.databaseBuilder(
            context,
            DrugDatabase::class.java,
            "drug_dose_db"
        ).build()
    }

    @Provides
    fun provideDrugDao(db: DrugDatabase): DrugDao {
        return db.drugDao()
    }
}