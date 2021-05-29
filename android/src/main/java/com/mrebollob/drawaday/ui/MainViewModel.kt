package com.mrebollob.drawaday.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
//    userRepository: UserRepository
) : ViewModel() {

    val isNewUser = flow<Boolean> { emit(true) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
}
