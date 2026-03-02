package net.firzen.android.cv.presentation.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.firzen.android.cv.domain.GetProfileDataUseCase
import net.firzen.android.cv.domain.model.*
import net.firzen.android.cv.other.getSupportedLocaleCode
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
// The locale is held in a MutableStateFlow so that calling refreshLocale() after a
// system language change triggers flatMapLatest to re-subscribe with the new locale.
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDataUseCase: GetProfileDataUseCase
) : ViewModel() {

    private val locale = MutableStateFlow(getSupportedLocaleCode())

    val state: StateFlow<ProfileScreenState> = locale
        .flatMapLatest { lang ->
            getProfileDataUseCase(lang).map { data ->
                Timber.i("Profile data loaded: ${data.profile?.name}")
                ProfileScreenState(
                    profile = data.profile,
                    languages = data.languages,
                    personalityTraits = data.personalityTraits,
                    interests = data.interests,
                    isLoading = false
                )
            }
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

    /** Call from ON_RESUME to pick up system locale changes. */
    fun refreshLocale() {
        locale.value = getSupportedLocaleCode()
    }
}
