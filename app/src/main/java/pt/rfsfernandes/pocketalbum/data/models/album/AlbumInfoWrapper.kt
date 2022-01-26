package pt.rfsfernandes.pocketalbum.data.models.album

import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketalbum.data.models.error.ErrorResponse
import pt.rfsfernandes.pocketalbum.data.models.generic.LastFMAttr

/**
 *   Class AlbumInfoWrapper created at 22/01/2022 23:07 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class AlbumInfoWrapper(
  @SerializedName("album") val album: Album? = null,
  @SerializedName("@attr") val attr: LastFMAttr? = null
) : ErrorResponse()
