package pt.rfsfernandes.pocketalbum.data.models.artist

import com.google.gson.annotations.SerializedName

/**
 *   Class ArtistMatches created at 22/01/2022 21:08 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class ArtistMatches(
  @SerializedName("artistmatches") val artistMatches: ArtistsWrapper? = null
)