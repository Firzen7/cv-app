package net.firzen.android.cv.domain

import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.Project
import javax.inject.Inject

/**
 * Use case that loads a single project by its ID, including milestones.
 */
class GetProjectByIdUseCase @Inject constructor(
    private val repository: CvRepository
) {

    suspend operator fun invoke(projectId: Int): Project? {
        return repository.getProjectById(projectId)
    }
}
