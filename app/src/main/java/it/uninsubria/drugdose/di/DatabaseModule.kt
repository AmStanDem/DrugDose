package it.uninsubria.drugdose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.uninsubria.drugdose.data.local.DrugDatabase
import it.uninsubria.drugdose.data.local.DatabaseCallback
import it.uninsubria.drugdose.data.local.dao.DrugDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        daoProvider: Provider<DrugDao>,
        scope: CoroutineScope
    ): DrugDatabase {
        return Room.databaseBuilder(
            context,
            DrugDatabase::class.java,
            "drug_dose_db"
        )
            .addCallback(DatabaseCallback(context, daoProvider, scope))
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideDrugDao(db: DrugDatabase): DrugDao {
        return db.drugDao()
    }
}