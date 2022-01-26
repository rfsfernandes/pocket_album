package pt.rfsfernandes.pocketalbum.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.rfsfernandes.pocketalbum.BuildConfig
import pt.rfsfernandes.pocketalbum.data.local.LastFMDao
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist
import pt.rfsfernandes.pocketalbum.data.remote.apiservice.LastFMService
import pt.rfsfernandes.pocketalbum.utils.Resource

/**
 *   Class RepositoryImpl created at 22/01/2022 20:40 for the project PocketAlbum
 *   By: rodrigofernandes
 */
open class RepositoryImpl(
  private val lastFMService: LastFMService,
  private val dao: LastFMDao
) : Repository {

  private val limit = 20
  private val apiKey = BuildConfig.apiKey

  override fun searchForArtists(searchTerm: String, page: Int):
      Flow<Resource<List<Artist>>> = flow {

    if (page <= 1)
      dao.cleanCachedArtists()
    else
      emit(Resource.Success(dao.searchForArtist()))

    try {
      val response = lastFMService.searchArtist(
        apiKey, ARTIST_SEARCH, artist =
        searchTerm, page = page, limit = limit
      )

      if (response.isSuccessful) {
        response.body()?.results?.artistMatches?.artistsWrapper?.let {

          if (page <= 1) {
            dao.cleanCachedArtists()
          }
          dao.insertArtists(it)
          val artists = dao.searchForArtist()
          emit(Resource.Success(artists))
        }
      } else {
        response.body()?.results?.message?.let {
          emit(Resource.Error(it))
        }
      }
    } catch (e: Exception) {
      if (e.message.toString().contains("hostname"))
        emit(Resource.NetworkError(errorCode = 404))
    }

  }

  override suspend fun getStoredAlbums(): Flow<List<Album>> {

    return dao.getStoredAlbums()
  }

  override fun getAlbumInfo(albumName: String?, artistName: String?): Flow<Resource<Album>> = flow {
    var isSaved = false
    val info = dao.getAlbumInfo(albumName, artistName)
    if (info?.tracks != null && info.wiki != null) {
      emit(Resource.Success(info))
      isSaved = true
    }
    try {
      val response = lastFMService.getAlbumInfo(apiKey, ALBUM_INFO, artistName, albumName)

      if (response.isSuccessful) {
        response.body()?.album?.let {
          if (!isSaved)
            emit(Resource.Success(it))
          else {
            it.isFavorite = true
            it.imagePath = info?.imagePath
            dao.insertAlbum(it)
          }
        }
      } else {
        response.body()?.message?.let {
          emit(Resource.Error(it))
        }
      }
    } catch (e: Exception) {
      if (e.message.toString().contains("hostname")) {
        if (isSaved) {
          emit(Resource.NetworkError(errorCode = 401))
        } else {
          emit(Resource.NetworkError(errorCode = 404))
        }
      } else {
        emit(Resource.Error("An unexpected error occurred. Please try again later"))
      }
    }

  }

  override suspend fun addAlbumToFavorites(album: Album) {
    dao.insertAlbum(album)
  }

  override suspend fun removeAlbumFromFavorites(albumName: String?, artistName: String?) {
    dao.removeAlbumFromDB(albumName, artistName)
  }

  override fun getTopAlbums(artistName: String?, mbid: String?, page: Int):
      Flow<Resource<List<TopAlbum>>> = flow {

    if (page <= 1)
      dao.cleanCachedTopAlbums()

    try {
      val response = lastFMService.getTopAlbums(apiKey, TOP_ALBUMS, artistName, page, limit)

      if (response.isSuccessful) {
        response.body()?.topAlbums?.albumList?.let {

          if (page <= 1) {
            dao.cleanCachedTopAlbums()
          }
          dao.insertTopAlbums(it)
          emit(Resource.Success(dao.getTopAlbums()))
        }
      } else {
        response.body()?.topAlbums?.message?.let {
          emit(Resource.Error(it))
        }
      }
    } catch (e: Exception) {
      if (e.message.toString().contains("hostname"))
        emit(Resource.NetworkError(errorCode = 404))
    }

  }

}
