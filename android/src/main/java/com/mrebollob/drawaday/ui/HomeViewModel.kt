package com.mrebollob.drawaday.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.drawaday.domain.repository.DrawADayRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    drawRepository: DrawADayRepository
) : ViewModel() {

    val drawImages = drawRepository.fetchDrawImages()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
