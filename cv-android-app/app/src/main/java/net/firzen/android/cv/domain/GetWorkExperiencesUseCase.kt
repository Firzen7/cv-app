package net.firzen.android.cv.domain

import kotlinx.coroutines.flow.Flow
import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.WorkExperience
import javax.inject.Inject

/**
 * Use case that loads all work experience entries for the Experience screen.
 *
 * Returns a [Flow] that automatically re-emits when the work_experience table changes.
 */
class GetWorkExperiencesUseCase @Inject constructor(
    private val repository: CvRepository
) {

    operator fun invoke(): Flow<List<WorkExperience>> {
        return repository.getAllWorkExperiences()
    }
}
