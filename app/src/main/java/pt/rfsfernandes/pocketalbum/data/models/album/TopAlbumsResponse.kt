package pt.rfsfernandes.pocketalbum.data.models.album

import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketalbum.data.models.error.ErrorResponse

/**
 *   Class TopAlbumsResponse created at 22/01/2022 23:06 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class TopAlbumsResponse(
  @SerializedName("topalbums") val topAlbums: TopAlbumWrapper? = null
) : ErrorResponse()
