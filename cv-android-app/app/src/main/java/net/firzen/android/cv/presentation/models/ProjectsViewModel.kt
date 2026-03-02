package net.firzen.android.cv.presentation.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.firzen.android.cv.domain.GetProjectsUseCase
import net.firzen.android.cv.domain.model.Project
import net.firzen.android.cv.other.getSupportedLocaleCode
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

// UI state holding all data needed by the Projects screen
data class ProjectsScreenState(
    val projects: List<Project> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel exposes projects as a StateFlow derived from the reactive use case.
@HiltViewModel
class ProjectsViewModel @Inject constructor(
    getProjectsUseCase: GetProjectsUseCase
) : ViewModel() {

    val state: StateFlow<ProjectsScreenState> = getProjectsUseCase(getSupportedLocaleCode())
        .map { projects ->
            Timber.i("Projects loaded: ${projects.size} entries")
            ProjectsScreenState(
                projects = projects,
                isLoading = false
            )
        }
        .catch { e ->
            Timber.e(e, "Error loading projects")
            emit(ProjectsScreenState(error = e.message, isLoading = false))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProjectsScreenState()
        )
}
