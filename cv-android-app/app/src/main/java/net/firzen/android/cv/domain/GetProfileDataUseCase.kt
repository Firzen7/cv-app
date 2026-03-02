package net.firzen.android.cv.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.*
import javax.inject.Inject

/**
 * Use case that loads all data needed by the Profile screen.
 *
 * Returns a [Flow] that combines profile, languages, personality traits, and
 * interests. The Flow automatically re-emits when any underlying table changes.
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

    operator fun invoke(language: String): Flow<ProfileData> {
        return combine(
            repository.getProfile(language),
            repository.getAllLanguages(language),
            repository.getAllPersonalityTraits(language),
            repository.getAllInterests(language)
        ) { profile, languages, traits, interests ->
            ProfileData(
                profile = profile,
                languages = languages,
                personalityTraits = traits,
                interests = interests
            )
        }
    }
}
