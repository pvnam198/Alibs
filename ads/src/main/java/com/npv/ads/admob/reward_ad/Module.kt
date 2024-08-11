package com.npv.ads.admob.reward_ad

import android.content.Context
import com.npv.ads.admob.revenue_tracker.RewardRevenueTracker
import com.npv.ads.admob.reward_ad.managers.RewardAdManager
import com.npv.ads.admob.reward_ad.managers.RewardAdManagerImpl
import com.npv.ads.admob.reward_ad.repositories.RewardAdRepository
import com.npv.ads.admob.reward_ad.repositories.RewardAdRepositoryImpl
import com.npv.ads.section_loader.createSectionLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Singleton
    @Provides
    fun provideRewardRepository(
        @ApplicationContext context: Context,
        tracker: RewardRevenueTracker
    ): RewardAdRepository {
        return RewardAdRepositoryImpl(
            context = context,
            sessionLoader = createSectionLoader(),
            rewardRevenueTracker = tracker
        )
    }

    @Singleton
    @Provides
    fun provideRewardAdManager(repo: RewardAdRepository): RewardAdManager {
        return RewardAdManagerImpl(
            rewardAdRepository = repo
        )
    }

}