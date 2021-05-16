package com.mrebollob.drawaday.di

import com.mrebollob.drawaday.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get(),get()) }
}
