package pt.rfsfernandes.pocketalbum.domain.use_cases

import pt.rfsfernandes.pocketalbum.data.repository.Repository

/**
 *   Class RemoveAlbumFromList created at 24/01/2022 23:31 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class RemoveAlbumFromList(private val repository: Repository) {

  suspend operator fun invoke(albumName: String?, artistName: String?) {
    repository.removeAlbumFromFavorites(albumName, artistName)
  }

}