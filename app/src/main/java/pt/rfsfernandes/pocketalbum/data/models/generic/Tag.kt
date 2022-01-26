package pt.rfsfernandes.pocketalbum.data.models.generic

import com.google.gson.annotations.SerializedName

/**
 *   Class Tag created at 22/01/2022 22:30 for the project PocketAlbum
 *   By: rodrigofernandes
 */
data class Tag(

  @SerializedName("url") val url: String? = null,
  @SerializedName("name") val name: String? = null

)