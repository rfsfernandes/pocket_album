package pt.rfsfernandes.pocketalbum.data.models.generic

import com.google.gson.annotations.SerializedName

/**
 *   Class LastFMImage created at 22/01/2022 21:11 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class LastFMImage(
  @SerializedName("#text") val text: String? = null,
  @SerializedName("size") val size: String? = null
)
