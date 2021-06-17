package com.mrebollob.drawaday.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.drawaday.shared.domain.model.User
import com.mrebollob.drawaday.shared.domain.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {


}
