package pt.rfsfernandes.pocketalbum.presentation.my_albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.domain.use_cases.GetStoredAlbums
import javax.inject.Inject

@HiltViewModel
class MyAlbumsViewModel @Inject constructor(private val getStoredAlbums: GetStoredAlbums) :
  ViewModel() {

  private val _storedAlbums: MutableStateFlow<List<Album>> = MutableStateFlow(listOf())
  val storedAlbums: StateFlow<List<Album>> = _storedAlbums.asStateFlow()

  fun fetchStoredAlbums() {
    viewModelScope.launch {
      getStoredAlbums().collect { newList ->
        _storedAlbums.update {
          newList
        }
      }
    }

  }


}