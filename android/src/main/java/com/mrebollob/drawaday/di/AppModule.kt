package com.mrebollob.drawaday.di

import com.mrebollob.drawaday.ui.MainViewModel
import com.mrebollob.drawaday.ui.home.feed.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
    viewModel { FeedViewModel(get()) }
}
