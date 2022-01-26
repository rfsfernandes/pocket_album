package pt.rfsfernandes.pocketalbum.data.repository

import kotlinx.coroutines.flow.Flow
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist
import pt.rfsfernandes.pocketalbum.utils.Resource

interface Repository {
  fun searchForArtists(searchTerm: String, page: Int): Flow<Resource<List<Artist>>>
  suspend fun getStoredAlbums(): Flow<List<Album>>
  fun getAlbumInfo(albumName: String?, artistName: String?): Flow<Resource<Album>>
  suspend fun addAlbumToFavorites(album: Album)
  suspend fun removeAlbumFromFavorites(albumName: String?, artistName: String?)
  fun getTopAlbums(artistName: String?, mbid: String?, page: Int): Flow<Resource<List<TopAlbum>>>
}