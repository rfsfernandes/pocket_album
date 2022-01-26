package pt.rfsfernandes.pocketalbum.utils

/**
 *   Class Resource created at 23/01/2022 19:43 for the project PocketAlbum
 *   By: rodrigofernandes
 */
typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(
  val data: T? = null,
  val message: String? = null
) {
  class Success<T>(data: T? = null) : Resource<T>(data)
  class Error<T>(message: String? = null, data: T? = null) : Resource<T>(data, message)
  class NetworkError<T>(val errorCode: Int) : Resource<T>()
  class InitialState<T> : Resource<T>()
}