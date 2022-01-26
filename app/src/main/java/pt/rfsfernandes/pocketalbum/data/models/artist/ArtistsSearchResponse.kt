package pt.rfsfernandes.pocketalbum.data.models.artist

import com.google.gson.annotations.SerializedName

/**
 *   Class ArtistsSearchResponse created at 23/01/2022 16:28 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class ArtistsSearchResponse(
  @SerializedName("results") val results: ArtistSearch? = null
)
