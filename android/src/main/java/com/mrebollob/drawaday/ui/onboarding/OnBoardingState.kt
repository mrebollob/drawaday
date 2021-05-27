package com.mrebollob.drawaday.ui.onboarding

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class OnBoardingContentState(
    val content: OnBoardingContent,
    val index: Int,
    val totalCount: Int,
    val showPrevious: Boolean,
    val showDone: Boolean
)

data class OnBoardingState(
    val contentStates: List<OnBoardingContentState>
) {
    var currentIndex by mutableStateOf(0)

    companion object {
        fun newInstance(): OnBoardingState {
            val contentStates = mutableListOf<OnBoardingContentState>()
            val content = OnBoardingContent.getOnBoardingContent()
            content.forEachIndexed { index, onBoardingContent ->
                contentStates.add(
                    OnBoardingContentState(
                        content = onBoardingContent,
                        index = index,
                        totalCount = content.size,
                        showPrevious = index > 0,
                        showDone = index == content.size - 1
                    )
                )
            }
            return OnBoardingState(
                contentStates = contentStates
            )
        }
    }
}
