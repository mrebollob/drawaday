package com.mrebollob.drawaday.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val isNewUser = userRepository.getIsNewUser()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun setStartDate() {
        viewModelScope.launch {
            userRepository.setStartDate(DateTime.now())
        }
    }
}
