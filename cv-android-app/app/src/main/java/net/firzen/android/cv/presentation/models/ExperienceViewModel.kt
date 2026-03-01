package net.firzen.android.cv.presentation.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.firzen.android.cv.domain.GetWorkExperiencesUseCase
import net.firzen.android.cv.domain.model.WorkExperience
import timber.log.Timber
import javax.inject.Inject

// UI state holding all data needed by the Experience screen
data class ExperienceScreenState(
    val experiences: List<WorkExperience> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel loads work experiences via use case and exposes as Compose state.
@HiltViewModel
class ExperienceViewModel @Inject constructor(
    private val getWorkExperiencesUseCase: GetWorkExperiencesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ExperienceScreenState())
    val state: State<ExperienceScreenState> get() = _state

    init {
        loadExperiences()
    }

    private fun loadExperiences() {
        viewModelScope.launch {
            try {
                val experiences = getWorkExperiencesUseCase()

                _state.value = ExperienceScreenState(
                    experiences = experiences,
                    isLoading = false
                )

                Timber.i("Work experiences loaded: ${experiences.size} entries")
            } catch (e: Exception) {
                Timber.e(e, "Error loading work experiences")
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}
