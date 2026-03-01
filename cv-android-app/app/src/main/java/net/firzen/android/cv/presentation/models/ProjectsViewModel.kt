package net.firzen.android.cv.presentation.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.firzen.android.cv.domain.GetProjectsUseCase
import net.firzen.android.cv.domain.model.Project
import timber.log.Timber
import javax.inject.Inject

// UI state holding all data needed by the Projects screen
data class ProjectsScreenState(
    val projects: List<Project> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel loads projects via use case and exposes as Compose state.
@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProjectsScreenState())
    val state: State<ProjectsScreenState> get() = _state

    init {
        loadProjects()
    }

    private fun loadProjects() {
        viewModelScope.launch {
            try {
                val projects = getProjectsUseCase()

                _state.value = ProjectsScreenState(
                    projects = projects,
                    isLoading = false
                )

                Timber.i("Projects loaded: ${projects.size} entries")
            } catch (e: Exception) {
                Timber.e(e, "Error loading projects")
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}
