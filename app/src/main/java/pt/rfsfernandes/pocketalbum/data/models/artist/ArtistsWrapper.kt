package pt.rfsfernandes.pocketalbum.data.models.artist

import com.google.gson.annotations.SerializedName

/**
 *   Class ArtistsWrapper created at 22/01/2022 21:09 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class ArtistsWrapper(
  @SerializedName("artist") val artistsWrapper: List<Artist>? = null
)
