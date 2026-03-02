package net.firzen.android.cv.domain

import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.Project
import javax.inject.Inject

/**
 * Use case that loads all projects with their milestones for the Projects screen.
 *
 * Returns a [Flow] that automatically re-emits when projects or milestones change.
 */
class GetProjectsUseCase @Inject constructor(
    private val repository: CvRepository
) {

    operator fun invoke(): Flow<List<Project>> {
        return repository.getAllProjects()
    }
}
