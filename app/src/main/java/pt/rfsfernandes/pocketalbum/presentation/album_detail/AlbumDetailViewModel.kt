package pt.rfsfernandes.pocketalbum.presentation.album_detail

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pt.rfsfernandes.pocketalbum.R
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.domain.use_cases.AddAlbumToList
import pt.rfsfernandes.pocketalbum.domain.use_cases.GetAlbumInfo
import pt.rfsfernandes.pocketalbum.domain.use_cases.RemoveAlbumFromList
import pt.rfsfernandes.pocketalbum.utils.Resource
import pt.rfsfernandes.pocketalbum.utils.ui.UIEvent
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
  private val fetchAlbumInfo: GetAlbumInfo,
  private val removeAlbumFromList: RemoveAlbumFromList,
  private val addAlbumToList: AddAlbumToList
) : ViewModel() {

  private val _albumInfoResource: MutableStateFlow<Resource<Album>> =
    MutableStateFlow(Resource.InitialState())
  val albumInfoResource: StateFlow<Resource<Album>> = _albumInfoResource.asStateFlow()

  private val _addAlbumResource: MutableStateFlow<Resource<Boolean>> = MutableStateFlow(
    Resource
      .InitialState()
  )
  val addAlbumResource: StateFlow<Resource<Boolean>> = _addAlbumResource.asStateFlow()


  private val _eventFlow = MutableSharedFlow<UIEvent>()
  val eventFlow: SharedFlow<UIEvent> = _eventFlow.asSharedFlow()

  fun getAlbumInfo(artistName: String, albumName: String) {
    viewModelScope.launch {
      _eventFlow.emit(UIEvent.Loading(true))
      fetchAlbumInfo(albumName, artistName).collect { resource ->
        _albumInfoResource.update {
          resource
        }
        _eventFlow.emit(UIEvent.Loading(false))
      }
    }
  }

  fun addToFavorites(context: Context?, bitmap: Bitmap, album: Album) {
    try {

      val root = context?.getExternalFilesDir(null)?.absolutePath
      val pathStr = "images"
      var myDir =
        File("$root/$pathStr")
      if (!myDir.exists()) {
        myDir.mkdirs()
      }
      val name = "${Calendar.getInstance().timeInMillis}.jpg"
      myDir = File(myDir, name)
      val out = FileOutputStream(myDir)
      bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
      out.flush()
      out.close()
      viewModelScope.launch {
        album.imagePath = myDir.path.toString()
        album.isFavorite = true
        addAlbumToList(album)
        _eventFlow.emit(UIEvent.Loading(false))
        _eventFlow.emit(UIEvent.Snackbar(context?.getString(R.string.album_save_success)))
        _addAlbumResource.update {
          Resource.Success(true)
        }
      }
    } catch (e: Exception) {
      viewModelScope.launch {
        _eventFlow.emit(UIEvent.Loading(false))
        _eventFlow.emit(UIEvent.Snackbar(context?.getString(R.string.album_save_fail)))
        _addAlbumResource.update {
          Resource.Error(data = false)
        }
      }
    }

  }

  fun removeFromFavorites(
    context: Context?, artistName: String, albumName: String, imageURI: String
  ) {
    viewModelScope.launch {
      try {
        val file = File(imageURI)
        file.delete()
        removeAlbumFromList(albumName, artistName)
        _eventFlow.emit(UIEvent.Loading(false))
        _eventFlow.emit(UIEvent.Snackbar(context?.getString(R.string.album_delete_success)))
        _addAlbumResource.update {
          Resource.Success(false)
        }
      } catch (e: Exception) {
        viewModelScope.launch {
          _eventFlow.emit(UIEvent.Loading(false))
          _eventFlow.emit(UIEvent.Snackbar(context?.getString(R.string.album_save_fail)))
          _addAlbumResource.update {
            Resource.Error(data = false)
          }
        }
      }
    }
  }

}