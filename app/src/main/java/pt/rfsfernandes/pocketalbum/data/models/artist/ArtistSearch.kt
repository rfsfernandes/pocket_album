package pt.rfsfernandes.pocketalbum.data.models.artist

import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketalbum.data.models.error.ErrorResponse

/**
 *   Class ArtistSearch created at 22/01/2022 21:14 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class ArtistSearch(
  @SerializedName("opensearch:totalResults") val totalResults: String? = null,
  @SerializedName("opensearch:startIndex") val startIndex: String? = null,
  @SerializedName("opensearch:itemsPerPage") val itemsPerPage: String? = null,
  @SerializedName("artistmatches") val artistMatches: ArtistsWrapper? = null,
) : ErrorResponse()
