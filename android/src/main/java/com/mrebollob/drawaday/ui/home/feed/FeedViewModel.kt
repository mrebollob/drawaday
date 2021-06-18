package com.mrebollob.drawaday.ui.home.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
import com.mrebollob.drawaday.state.UiState
import com.mrebollob.drawaday.state.copyWithResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FeedViewModel(
    private val drawRepository: DrawADayRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _drawImages = MutableStateFlow(UiState(data = emptyList<DrawImage>()))
    val drawImages: StateFlow<UiState<List<DrawImage>>> = _drawImages.asStateFlow()

    init {
        loadImages(false)
    }

    fun loadImages(refresh: Boolean) {
        viewModelScope.launch {
            userRepository.getDaysInTheApp().map { index ->
                drawRepository.fetchDrawImages(index, refresh)
            }.flattenConcat().collect { imagesResult ->
                _drawImages.value = drawImages.value.copyWithResult(imagesResult)
            }
        }
    }
}
