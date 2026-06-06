package it.uninsubria.drugdose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.uninsubria.drugdose.data.local.dao.DrugDao
import it.uninsubria.drugdose.data.repository.DrugRepositoryImpl
import it.uninsubria.drugdose.domain.repository.DrugRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDrugRepository(
        dao: DrugDao
    ): DrugRepository {
        return DrugRepositoryImpl(dao)
    }
}