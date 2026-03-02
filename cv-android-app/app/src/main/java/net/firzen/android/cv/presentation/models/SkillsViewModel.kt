package net.firzen.android.cv.presentation.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.firzen.android.cv.domain.GetSkillsDataUseCase
import net.firzen.android.cv.domain.model.*
import timber.log.Timber
import javax.inject.Inject

// UI state holding all data needed by the Skills screen
data class SkillsScreenState(
    val programmingLanguages: List<ProgrammingLanguage> = emptyList(),
    val technologyCategories: List<TechnologyCategory> = emptyList(),
    val otherSkillCategories: List<OtherSkillCategory> = emptyList(),
    val education: List<Education> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel exposes skills data as a StateFlow derived from the reactive use case.
@HiltViewModel
class SkillsViewModel @Inject constructor(
    getSkillsDataUseCase: GetSkillsDataUseCase
) : ViewModel() {

    val state: StateFlow<SkillsScreenState> = getSkillsDataUseCase()
        .map { data ->
            Timber.i("Skills data loaded: ${data.programmingLanguages.size} languages, " +
                    "${data.technologyCategories.size} tech categories")
            SkillsScreenState(
                programmingLanguages = data.programmingLanguages,
                technologyCategories = data.technologyCategories,
                otherSkillCategories = data.otherSkillCategories,
                education = data.education,
                isLoading = false
            )
        }
        .catch { e ->
            Timber.e(e, "Error loading skills data")
            emit(SkillsScreenState(error = e.message, isLoading = false))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SkillsScreenState()
        )
}
