package net.firzen.android.cv.presentation.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.firzen.android.cv.domain.GetProjectByIdUseCase
import net.firzen.android.cv.domain.model.Project
import timber.log.Timber
import javax.inject.Inject

// UI state holding data for the Project Detail screen
data class ProjectDetailScreenState(
    val project: Project? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel loads a single project by ID from the navigation argument.
@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getProjectByIdUseCase: GetProjectByIdUseCase
) : ViewModel() {

    private val projectId: Int = savedStateHandle.get<Int>("projectId") ?: -1

    val state: StateFlow<ProjectDetailScreenState> = getProjectByIdUseCase(projectId)
        .map { project ->
            Timber.i("Project detail loaded: ${project?.name}")
            ProjectDetailScreenState(
                project = project,
                isLoading = false
            )
        }
        .catch { e ->
            Timber.e(e, "Error loading project detail")
            emit(ProjectDetailScreenState(error = e.message, isLoading = false))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProjectDetailScreenState()
        )
}
