package pt.rfsfernandes.pocketalbum.data.models.album

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import pt.rfsfernandes.pocketalbum.data.models.artist.Artist
import pt.rfsfernandes.pocketalbum.data.models.generic.LastFMImage
import pt.rfsfernandes.pocketalbum.data.models.generic.TracksWrapper
import pt.rfsfernandes.pocketalbum.data.models.generic.Wiki

/**
 *   Class TopAlbum created at 22/01/2022 23:31 for the project PocketAlbum
 *   By: rodrigofernandes
 */
@Entity
data class TopAlbum(
  @PrimaryKey
  @NonNull
  @SerializedName("url")
  var url: String,
  @SerializedName("artist")
  @Embedded(prefix = "artist_")
  var artist: Artist? = null,
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
)
