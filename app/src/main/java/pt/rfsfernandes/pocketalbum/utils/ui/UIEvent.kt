package pt.rfsfernandes.pocketalbum.utils.ui

/**
 *   Class UIEvent created at 25/01/2022 02:06 for the project PocketAlbum
 *   By: rodrigofernandes
 */
sealed class UIEvent {
  data class Loading(val isLoading: Boolean) : UIEvent()
  data class Snackbar(val text: String?) : UIEvent()
}
