package pt.rfsfernandes.pocketalbum.data.models.album

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketalbum.data.models.generic.LastFMImage
import pt.rfsfernandes.pocketalbum.data.models.generic.TracksWrapper
import pt.rfsfernandes.pocketalbum.data.models.generic.Wiki

/**
 *   Class Album created at 22/01/2022 22:29 for the project PocketAlbum
 *   By: rodrigofernandes
 */
@Entity
data class Album(
  @PrimaryKey
  @NonNull
  @SerializedName("url")
  var url: String,
  @SerializedName("artist")
  var artist: String? = null,
  @SerializedName("mbid")
  var mbid: String? = null,
  @SerializedName("playcount")
  var playcount: Int? = null,
  @SerializedName("image")
  var image: List<LastFMImage> = listOf(),
  @SerializedName("tracks")
  var tracks: TracksWrapper? = null,
  @SerializedName("name")
  var name: String? = null,
  @SerializedName("listeners")
  var listeners: String? = null,
  @SerializedName("wiki")
  var wiki: Wiki? = null
) {
  var isFavorite = false
  var imagePath: String? = null

}
