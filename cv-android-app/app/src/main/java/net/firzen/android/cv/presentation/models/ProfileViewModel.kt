package net.firzen.android.cv.presentation.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.firzen.android.cv.domain.GetProfileDataUseCase
import net.firzen.android.cv.domain.model.*
import timber.log.Timber
import javax.inject.Inject

// UI state holding all data needed by the Profile screen
data class ProfileScreenState(
    val profile: Profile? = null,
    val languages: List<Language> = emptyList(),
    val personalityTraits: List<PersonalityTrait> = emptyList(),
    val interests: List<Interest> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel exposes profile data as a StateFlow derived from the reactive use case.
// The Flow emits automatically whenever the underlying DB tables change — fixing
// the first-launch empty-screen bug.
@HiltViewModel
class ProfileViewModel @Inject constructor(
    getProfileDataUseCase: GetProfileDataUseCase
) : ViewModel() {

    val state: StateFlow<ProfileScreenState> = getProfileDataUseCase()
        .map { data ->
            Timber.i("Profile data loaded: ${data.profile?.name}")
            ProfileScreenState(
                profile = data.profile,
                languages = data.languages,
                personalityTraits = data.personalityTraits,
                interests = data.interests,
                isLoading = false
            )
        }
        .catch { e ->
            Timber.e(e, "Error loading profile data")
            emit(ProfileScreenState(error = e.message, isLoading = false))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileScreenState()
        )
}
