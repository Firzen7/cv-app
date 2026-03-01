package net.firzen.android.cv.presentation.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.firzen.android.cv.data.local.CvDao
import net.firzen.android.cv.data.local.entities.*
import timber.log.Timber
import javax.inject.Inject

// UI state holding all data needed by the Profile screen
data class ProfileScreenState(
    val profile: ProfileEntity? = null,
    val languages: List<LanguageEntity> = emptyList(),
    val personalityTraits: List<PersonalityTraitEntity> = emptyList(),
    val interests: List<InterestEntity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

// ViewModel loads profile data from Room DB and exposes it as Compose state.
// @HiltViewModel tells Hilt to inject the DAO via constructor.
@HiltViewModel
class ProfileViewModel @Inject constructor(private val dao: CvDao) : ViewModel() {

    private val _state = mutableStateOf(ProfileScreenState())
    val state: State<ProfileScreenState> get() = _state

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            try {
                val profile = dao.getProfile()
                val languages = dao.getAllLanguages()
                val traits = dao.getAllPersonalityTraits()
                val interests = dao.getAllInterests()

                _state.value = ProfileScreenState(
                    profile = profile,
                    languages = languages,
                    personalityTraits = traits,
                    interests = interests,
                    isLoading = false
                )

                Timber.i("Profile data loaded: ${profile?.name}")
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
