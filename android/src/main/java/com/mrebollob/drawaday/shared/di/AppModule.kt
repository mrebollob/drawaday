package com.mrebollob.drawaday.shared.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.mrebollob.drawaday.analytics.AnalyticsManager
import com.mrebollob.drawaday.ui.MainViewModel
import com.mrebollob.drawaday.ui.drawing.DrawingViewModel
import com.mrebollob.drawaday.ui.home.feed.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FeedViewModel(get(), get()) }
    viewModel { parameters -> DrawingViewModel(get(), parameters.get()) }

    single { AnalyticsManager(get(), get()) }
    factory { FirebaseAnalytics.getInstance(get()) }
}
