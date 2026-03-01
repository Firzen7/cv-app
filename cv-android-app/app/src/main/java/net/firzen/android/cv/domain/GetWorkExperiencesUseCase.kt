package net.firzen.android.cv.domain

import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.WorkExperience
import javax.inject.Inject

/**
 * Use case that loads all work experience entries for the Experience screen.
 */
class GetWorkExperiencesUseCase @Inject constructor(
    private val repository: CvRepository
) {

    suspend operator fun invoke(): List<WorkExperience> {
        return repository.getAllWorkExperiences()
    }
}
