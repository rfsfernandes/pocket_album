package pt.rfsfernandes.pocketalbum.data.models.album

import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketalbum.data.models.error.ErrorResponse
import pt.rfsfernandes.pocketalbum.data.models.generic.LastFMAttr

/**
 *   Class TopAlbumWrapper created at 23/01/2022 21:18 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class TopAlbumWrapper(
  @SerializedName("album") val albumList: List<TopAlbum>? = null,
  @SerializedName("@attr") val attr: LastFMAttr? = null
) : ErrorResponse()