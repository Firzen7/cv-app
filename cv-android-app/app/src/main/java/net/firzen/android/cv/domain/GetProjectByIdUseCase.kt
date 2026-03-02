package net.firzen.android.cv.domain

import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.Project
import javax.inject.Inject

/**
 * Use case that loads a single project by its ID, including milestones.
 *
 * Returns a [Flow] that automatically re-emits when the project or its milestones change.
 */
class GetProjectByIdUseCase @Inject constructor(
    private val repository: CvRepository
) {

    operator fun invoke(projectId: Int): Flow<Project?> {
        return repository.getProjectById(projectId)
    }
}
