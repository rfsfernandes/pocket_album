package pt.rfsfernandes.pocketalbum.presentation.search_artist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist
import pt.rfsfernandes.pocketalbum.domain.use_cases.SearchForArtist
import pt.rfsfernandes.pocketalbum.utils.Resource
import pt.rfsfernandes.pocketalbum.utils.ui.UIEvent
import javax.inject.Inject

@HiltViewModel
class SearchArtistViewModel @Inject constructor(private val searchForArtist: SearchForArtist) :
  ViewModel() {

  private val _artistsResource: MutableStateFlow<Resource<List<Artist>>> =
    MutableStateFlow(Resource.InitialState())
  val artistResource: StateFlow<Resource<List<Artist>>> = _artistsResource.asStateFlow()
  private val _eventFlow = MutableSharedFlow<UIEvent>()
  val eventFlow: SharedFlow<UIEvent> = _eventFlow.asSharedFlow()

  private var page = 0

  fun searchArtist(searchTerm: String, resetPage: Boolean? = false) {

    if (resetPage == true)
      page = 1
    else
      page++
    viewModelScope.launch {
      _eventFlow.emit(UIEvent.Loading(true))
      searchForArtist(searchTerm, page).collect {
        _artistsResource.update { _ ->
          it
        }

        _eventFlow.emit(UIEvent.Loading(false))

      }
    }

  }

}