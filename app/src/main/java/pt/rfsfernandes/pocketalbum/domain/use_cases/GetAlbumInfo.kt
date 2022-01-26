package pt.rfsfernandes.pocketalbum.domain.use_cases

import kotlinx.coroutines.flow.Flow
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.repository.Repository
import pt.rfsfernandes.pocketalbum.utils.Resource

/**
 *   Class GetAlbumInfo created at 22/01/2022 22:56 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class GetAlbumInfo(private val repository: Repository) {

  operator fun invoke(albumName: String?, artistName: String?): Flow<Resource<Album>> {
    return repository.getAlbumInfo(albumName, artistName)
  }

}