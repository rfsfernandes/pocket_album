package pt.rfsfernandes.pocketalbum.domain.use_cases

import kotlinx.coroutines.flow.Flow
import pt.rfsfernandes.pocketalbum.data.models.album.TopAlbum
import pt.rfsfernandes.pocketalbum.data.repository.Repository
import pt.rfsfernandes.pocketalbum.utils.Resource

/**
 *   Class GetTopAlbums created at 22/01/2022 22:57 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class GetTopAlbums(private val repository: Repository) {

  suspend operator fun invoke(artistName: String?, mbid: String? = "", page: Int = 1):
      Flow<Resource<List<TopAlbum>>> {
    return repository.getTopAlbums(artistName, mbid, page)
  }

}