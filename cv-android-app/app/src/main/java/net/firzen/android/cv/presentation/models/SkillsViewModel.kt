package net.firzen.android.cv.presentation.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

// ViewModel loads skills data via use case and exposes as Compose state.
@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val getSkillsDataUseCase: GetSkillsDataUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SkillsScreenState())
    val state: State<SkillsScreenState> get() = _state

    init {
        loadSkills()
    }

    private fun loadSkills() {
        viewModelScope.launch {
            try {
                val data = getSkillsDataUseCase()

                _state.value = SkillsScreenState(
                    programmingLanguages = data.programmingLanguages,
                    technologyCategories = data.technologyCategories,
                    otherSkillCategories = data.otherSkillCategories,
                    education = data.education,
                    isLoading = false
                )

                Timber.i("Skills data loaded: ${data.programmingLanguages.size} languages, " +
                        "${data.technologyCategories.size} tech categories")
            } catch (e: Exception) {
                Timber.e(e, "Error loading skills data")
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}
