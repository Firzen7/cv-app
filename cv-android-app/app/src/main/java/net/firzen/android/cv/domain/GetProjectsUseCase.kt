package net.firzen.android.cv.domain

import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.Project
import javax.inject.Inject

/**
 * Use case that loads all projects with their milestones for the Projects screen.
 */
class GetProjectsUseCase @Inject constructor(
    private val repository: CvRepository
) {

    suspend operator fun invoke(): List<Project> {
        return repository.getAllProjects()
    }
}
