package pt.rfsfernandes.pocketalbum.presentation.top_albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum
import pt.rfsfernandes.pocketalbum.domain.use_cases.GetTopAlbums
import pt.rfsfernandes.pocketalbum.utils.Resource
import pt.rfsfernandes.pocketalbum.utils.ui.UIEvent
import javax.inject.Inject

@HiltViewModel
class TopAlbumsViewModel @Inject constructor(private val topAlbum: GetTopAlbums) : ViewModel() {

  private val _topAlbumsResource: MutableStateFlow<Resource<List<TopAlbum>>> =
    MutableStateFlow(Resource.InitialState())
  val topAlbumsResource: StateFlow<Resource<List<TopAlbum>>> = _topAlbumsResource.asStateFlow()
  private val _eventFlow = MutableSharedFlow<UIEvent>()
  val eventFlow: SharedFlow<UIEvent> = _eventFlow.asSharedFlow()

  var page = 0

  fun getTopAlbums(name: String, mbid: String, resetPage: Boolean? = false) {

    if (resetPage == true)
      page = 1
    else
      page++


    viewModelScope.launch {
      _eventFlow.emit(UIEvent.Loading(true))
      topAlbum(name, mbid, page).collect { resource ->
        _topAlbumsResource.update {
          resource
        }
        _eventFlow.emit(UIEvent.Loading(false))
      }
    }

  }

}