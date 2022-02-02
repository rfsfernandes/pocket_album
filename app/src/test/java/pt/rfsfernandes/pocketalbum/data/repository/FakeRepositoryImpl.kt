package pt.rfsfernandes.pocketalbum.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist
import pt.rfsfernandes.pocketalbum.utils.Resource

/**
 *   Class FakeRepositoryImpl created at 02/02/2022 22:06 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class FakeRepositoryImpl: Repository {

  val topAlbums = mutableListOf<TopAlbum>()
  val storedAlbums = mutableListOf<Album>()
  val artists = mutableListOf<Artist>()

  private val limit = 5

  override fun searchForArtists(searchTerm: String, page: Int): Flow<Resource<List<Artist>>> =
    flow  {

      when (searchTerm) {
        "404" -> {
          emit(Resource.NetworkError(errorCode = 404))
          return@flow
        }
        "401" -> {
          emit(Resource.NetworkError(errorCode = 401))
          return@flow
        }
        "undefined" -> {
          emit(Resource.Error("An unexpected error occurred. Please try again later"))
          return@flow
        }
        else -> {
          val artist: MutableList<Artist> = mutableListOf()
          for (item in artists) {
            if (item.name == searchTerm) {
              artist.add(item)
              break
            }
          }

          if(artist.isEmpty()) {
            emit(Resource.Error("Not found"))
          } else {
            emit(Resource.Success(artist))
          }

        }
      }

    }

  override suspend fun getStoredAlbums(): Flow<List<Album>> = flow {
    emit(storedAlbums)
  }

  override fun getAlbumInfo(albumName: String?, artistName: String?): Flow<Resource<Album>> = flow {
    when (albumName) {
      "404" -> {
        emit(Resource.NetworkError(errorCode = 404))
        return@flow
      }
      "401" -> {
        emit(Resource.NetworkError(errorCode = 401))
        return@flow
      }
      "undefined" -> {
        emit(Resource.Error("An unexpected error occurred. Please try again later"))
        return@flow
      }
      else -> {
        var album: Album? = null
        for (item in storedAlbums) {
          if (item.name == albumName && item.artist == artistName) {
            album = item
            break
          }
        }

        if(album == null) {
          emit(Resource.Error("Not found"))
        } else {
          emit(Resource.Success(album))
        }

      }
    }
  }

  override suspend fun addAlbumToFavorites(album: Album) {
    if(!storedAlbums.contains(album)) {
      storedAlbums.add(album)
    }
  }

  override suspend fun removeAlbumFromFavorites(albumName: String?, artistName: String?) {
    storedAlbums.removeIf {it.name == albumName && it.artist == artistName}
  }

  override fun getTopAlbums(
    artistName: String?,
    mbid: String?,
    page: Int
  ): Flow<Resource<List<TopAlbum>>> = flow {
    when (artistName) {
      "404" -> {
        emit(Resource.NetworkError(errorCode = 404))
        return@flow
      }
      "401" -> {
        emit(Resource.NetworkError(errorCode = 401))
        return@flow
      }
      "undefined" -> {
        emit(Resource.Error("An unexpected error occurred. Please try again later"))
        return@flow
      }
      else -> {
        val album: MutableList<TopAlbum> = mutableListOf()
        for (item in 0 until topAlbums.size) {
          if (topAlbums[item].artist?.name == artistName) {
            album.add(topAlbums[item])
          }
        }

        if(album.isEmpty()) {
          emit(Resource.Error("Not found"))
        } else {
          emit(Resource.Success(album))
        }

      }
    }
  }
}
