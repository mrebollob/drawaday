package com.mrebollob.drawaday.ui.home.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FeedViewModel(
    drawRepository: DrawADayRepository,
    userRepository: UserRepository
) : ViewModel() {

    val drawImages: StateFlow<List<DrawImage>> = drawRepository.fetchDrawImages()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val user = userRepository.getUser()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
