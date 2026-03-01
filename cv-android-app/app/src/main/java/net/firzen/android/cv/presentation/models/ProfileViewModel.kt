package net.firzen.android.cv.presentation.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

// ViewModel loads profile data via use case and exposes it as Compose state.
// @HiltViewModel tells Hilt to inject the use case via constructor.
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProfileScreenState())
    val state: State<ProfileScreenState> get() = _state

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            try {
                val data = getProfileDataUseCase()

                _state.value = ProfileScreenState(
                    profile = data.profile,
                    languages = data.languages,
                    personalityTraits = data.personalityTraits,
                    interests = data.interests,
                    isLoading = false
                )

                Timber.i("Profile data loaded: ${data.profile?.name}")
            } catch (e: Exception) {
                Timber.e(e, "Error loading profile data")
                _state.value = _state.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
}
