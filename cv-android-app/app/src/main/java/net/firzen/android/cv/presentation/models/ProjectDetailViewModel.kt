package net.firzen.android.cv.presentation.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val getProjectByIdUseCase: GetProjectByIdUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProjectDetailScreenState())
    val state: State<ProjectDetailScreenState> get() = _state

    init {
        val projectId = savedStateHandle.get<Int>("projectId") ?: -1
        loadProject(projectId)
    }

    private fun loadProject(projectId: Int) {
        viewModelScope.launch {
            try {
                val project = getProjectByIdUseCase(projectId)

                _state.value = ProjectDetailScreenState(
                    project = project,
                    isLoading = false
                )

                Timber.i("Project detail loaded: ${project?.name}")
            } catch (e: Exception) {
                Timber.e(e, "Error loading project detail")
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}
