package net.firzen.android.cv.presentation.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.firzen.android.cv.domain.GetWorkExperiencesUseCase
import net.firzen.android.cv.domain.model.WorkExperience
import net.firzen.android.cv.other.getSupportedLocaleCode
import timber.log.Timber
import javax.inject.Inject

// UI state holding all data needed by the Experience screen
data class ExperienceScreenState(
    val experiences: List<WorkExperience> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel exposes work experiences as a StateFlow derived from the reactive use case.
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExperienceViewModel @Inject constructor(
    private val getWorkExperiencesUseCase: GetWorkExperiencesUseCase
) : ViewModel() {

    private val locale = MutableStateFlow(getSupportedLocaleCode())

    val state: StateFlow<ExperienceScreenState> = locale
        .flatMapLatest { lang ->
            getWorkExperiencesUseCase(lang).map { experiences ->
                Timber.i("Work experiences loaded: ${experiences.size} entries")
                ExperienceScreenState(
                    experiences = experiences,
                    isLoading = false
                )
            }
        }
        .catch { e ->
            Timber.e(e, "Error loading work experiences")
            emit(ExperienceScreenState(error = e.message, isLoading = false))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExperienceScreenState()
        )

    /** Call from ON_RESUME to pick up system locale changes. */
    fun refreshLocale() {
        locale.value = getSupportedLocaleCode()
    }
}
