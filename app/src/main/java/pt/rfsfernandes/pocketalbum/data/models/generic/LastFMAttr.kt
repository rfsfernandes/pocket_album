package pt.rfsfernandes.pocketalbum.data.models.generic

import com.google.gson.annotations.SerializedName

/**
 *   Class LastFMAttr created at 22/01/2022 23:08 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class LastFMAttr(
  @SerializedName("artist") val artist: String? = null,
  @SerializedName("page") val page: String? = null,
  @SerializedName("perPage") val perPage: String? = null,
  @SerializedName("totalPages") val totalPages: String? = null,
  @SerializedName("total") val total: String? = null
)
