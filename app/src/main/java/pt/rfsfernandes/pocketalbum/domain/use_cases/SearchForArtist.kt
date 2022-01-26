package pt.rfsfernandes.pocketalbum.domain.use_cases

import kotlinx.coroutines.flow.Flow
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist
import pt.rfsfernandes.pocketalbum.data.repository.Repository
import pt.rfsfernandes.pocketalbum.utils.Resource

/**
 *   Class SearchForArtist created at 22/01/2022 20:57 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class SearchForArtist(private val repository: Repository) {

  operator fun invoke(searchTerm: String, page: Int): Flow<Resource<List<Artist>>> {
    return repository.searchForArtists(searchTerm, page)
  }

}
