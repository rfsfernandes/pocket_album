package pt.rfsfernandes.pocketalbum.data.models.generic

import com.google.gson.annotations.SerializedName

/**
 *   Class Streamable created at 22/01/2022 22:35 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class Streamable(
  @SerializedName("fulltrack") val fulltrack: String? = null,
  @SerializedName("#text") val text: String? = null
)
