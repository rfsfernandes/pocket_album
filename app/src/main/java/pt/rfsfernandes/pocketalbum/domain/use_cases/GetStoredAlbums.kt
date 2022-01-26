package pt.rfsfernandes.pocketalbum.domain.use_cases

import kotlinx.coroutines.flow.Flow
import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.repository.Repository

/**
 *   Class GetStoredAlbums created at 22/01/2022 20:51 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class GetStoredAlbums(private val repository: Repository) {

  suspend operator fun invoke(): Flow<List<Album>> {
    return repository.getStoredAlbums()
  }

}
