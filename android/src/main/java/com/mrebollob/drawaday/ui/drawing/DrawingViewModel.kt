package com.mrebollob.drawaday.ui.drawing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.drawaday.shared.domain.model.DrawImage
import com.mrebollob.drawaday.shared.domain.repository.DrawADayRepository
import com.mrebollob.drawaday.state.UiState
import com.mrebollob.drawaday.state.copyWithResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DrawingViewModel(
    private val drawRepository: DrawADayRepository,
    private val imageId: String
) : ViewModel() {

    private val _drawImage: MutableStateFlow<UiState<DrawImage>> = MutableStateFlow(UiState())
    val drawImage: StateFlow<UiState<DrawImage>> = _drawImage.asStateFlow()

    init {
        loadImage()
    }

    private fun loadImage() {
        viewModelScope.launch {
            drawRepository.fetchDrawImage(imageId).collect { imageResult ->
                _drawImage.value = drawImage.value.copyWithResult(imageResult)
            }
        }
    }
}
