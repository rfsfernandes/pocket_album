package pt.rfsfernandes.pocketalbum.data.models.generic

import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist

/**
 *   Class Track created at 22/01/2022 22:34 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class Track(
  @SerializedName("streamable") val streamable: Streamable? = null,
  @SerializedName("duration") val duration: Int? = null,
  @SerializedName("url") val url: String? = null,
  @SerializedName("name") val name: String? = null,
  @SerializedName("artist") val artist: Artist? = null
)
