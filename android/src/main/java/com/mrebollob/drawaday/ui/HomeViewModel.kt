package com.mrebollob.drawaday.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Kermit
import com.mrebollob.drawaday.remote.Assignment
import com.mrebollob.drawaday.remote.IssPosition
import com.mrebollob.drawaday.repository.DrawADayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val drawRepository: DrawADayRepository,
    private val logger: Kermit
) : ViewModel() {

    val peopleInSpace = drawRepository.fetchPeopleAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val issPosition: Flow<IssPosition> = drawRepository.pollISSPosition()

    fun getPersonBio(personName: String): String {
        return drawRepository.getPersonBio(personName)
    }

    fun getPersonImage(personName: String): String {
        return drawRepository.getPersonImage(personName)
    }

    fun getPerson(personName: String): Assignment? {
        return peopleInSpace.value.find { it.name == personName }
    }
}
