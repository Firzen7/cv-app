package net.firzen.android.cv.domain

import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.*
import javax.inject.Inject

/**
 * Use case that loads all data needed by the Profile screen.
 *
 * The `operator fun invoke()` pattern allows calling this use case
 * like a function: `val data = getProfileDataUseCase()`
 */
class GetProfileDataUseCase @Inject constructor(
    private val repository: CvRepository
) {

    data class ProfileData(
        val profile: Profile?,
        val languages: List<Language>,
        val personalityTraits: List<PersonalityTrait>,
        val interests: List<Interest>
    )

    suspend operator fun invoke(): ProfileData {
        return ProfileData(
            profile = repository.getProfile(),
            languages = repository.getAllLanguages(),
            personalityTraits = repository.getAllPersonalityTraits(),
            interests = repository.getAllInterests()
        )
    }
}
