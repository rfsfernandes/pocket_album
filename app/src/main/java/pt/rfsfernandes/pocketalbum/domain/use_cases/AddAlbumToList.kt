package pt.rfsfernandes.pocketalbum.domain.use_cases

import pt.rfsfernandes.pocketalbum.data.models.album.Album
import pt.rfsfernandes.pocketalbum.data.repository.Repository

/**
 *   Class AddAlbumToList created at 24/01/2022 23:31 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class AddAlbumToList(private val repository: Repository) {

  suspend operator fun invoke(album: Album) {
    repository.addAlbumToFavorites(album)
  }

}