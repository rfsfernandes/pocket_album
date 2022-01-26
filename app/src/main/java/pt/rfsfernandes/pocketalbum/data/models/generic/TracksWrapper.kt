package pt.rfsfernandes.pocketalbum.data.models.generic

import com.google.gson.annotations.SerializedName

/**
 *   Class TracksWrapper created at 22/01/2022 22:33 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class TracksWrapper(
  @SerializedName("track") val track: List<Track> = listOf()
)
