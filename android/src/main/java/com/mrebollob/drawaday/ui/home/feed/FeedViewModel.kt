package com.mrebollob.drawaday.ui.home.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FeedViewModel(
    private val drawRepository: DrawADayRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _drawImages = MutableStateFlow(emptyList<DrawImage>())
    val drawImages: StateFlow<List<DrawImage>> = _drawImages.asStateFlow()

    init {
        loadImages()
    }

    private fun loadImages() {
        viewModelScope.launch {
            userRepository.getDaysInTheApp().map { index ->
                drawRepository.fetchDrawImages(index)
            }.flattenConcat().collect { images ->
                _drawImages.value = images
            }
        }
    }
}
