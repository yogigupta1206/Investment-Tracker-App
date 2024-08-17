package com.yogigupta1206.investment_tracker_addepar.di

import android.content.Context
import com.yogigupta1206.investment_tracker_addepar.data.repository.InvestmentRepoImpl
import com.yogigupta1206.investment_tracker_addepar.domain.repository.InvestmentRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideInvestmentRepository(@ApplicationContext context: Context) : InvestmentRepo {
        return InvestmentRepoImpl(context)
    }

}